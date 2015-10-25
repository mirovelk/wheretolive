package xyz.wheretolive.crawl.geocoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.geocoding.Geocoder;
import xyz.wheretolive.core.geocoding.GoogleGeocodeResult;
import xyz.wheretolive.mongo.MongoGoogleGeocodeRepository;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Component
public class GoogleGeocoder implements Geocoder {

    private Logger logger = LogManager.getLogger(GoogleGeocoder.class);

    private static final String API_KEY = "AIzaSyCV8DEQzTGOuEp7DMd4cdUjvJaQlKpJCVE";

    @Autowired
    MongoGoogleGeocodeRepository repository;

    @Override
    public Coordinates translate(String address) {
        address = normalizeAddress(address);
        try {
            GoogleGeocodeResult fromCache = getFromCache(address);
            GoogleGeocodeResult result;
            if (fromCache != null) {
                result = fromCache;
            } else {
                result = query(address);
                cache(result);
            }
            return extract(result);
        } catch (Exception e) {
            logger.error("Error while translating address: '" + address + "'", e);
            return null;
        }
    }

    private String normalizeAddress(String address) {
        return address.replaceAll("\\s", " ");
    }

    private GoogleGeocodeResult getFromCache(String address) {
        return repository.find(address);
    }
    
    private GoogleGeocodeResult query(String address) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String encoded = URLEncoder.encode(address, "UTF-8");
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + encoded + "&key=" + API_KEY);
        Map<String, Object> map = mapper.readValue(url, Map.class);
        return new GoogleGeocodeResult(address, map);
    }
    
    private void cache(GoogleGeocodeResult result) {
        assert result.getQuery() != null;
        repository.store(result);
    }
    
    private Coordinates extract(GoogleGeocodeResult result) {
        Map<String, Object> map = result.getResult();
        List results = (List) map.get("results");
        String status = (String) map.get("status");
        if (!"OK".equals(status)) {
            throw new IllegalStateException("Google geocode didn't return OK result. Result was: " + status);
        }
        Map<String, Object> firstResult = (Map<String, Object>) results.get(0);
        Map<String, Object> geometry = (Map<String, Object>) firstResult.get("geometry");
        Map<String, Object> location = (Map<String, Object>) geometry.get("location");
        Double lat = (Double) location.get("lat");
        Double lng = (Double) location.get("lng");
        return new Coordinates(lat, lng);
    }
    
}
