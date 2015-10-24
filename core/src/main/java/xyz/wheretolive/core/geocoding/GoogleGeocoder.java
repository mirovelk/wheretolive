package xyz.wheretolive.core.geocoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import xyz.wheretolive.core.domain.Coordinates;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Component
public class GoogleGeocoder implements Geocoder {

    private Logger logger = LogManager.getLogger(GoogleGeocoder.class);

    private static final String API_KEY = "AIzaSyCV8DEQzTGOuEp7DMd4cdUjvJaQlKpJCVE";

    @Override
    public Coordinates translate(String address) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String encoded = URLEncoder.encode(address, "UTF-8");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + encoded + "&key=" + API_KEY);
            Map<String, Object> map = mapper.readValue(url, Map.class);
            List results = (List) map.get("results");
            Map<String, Object> firstResult = (Map<String, Object>) results.get(0);
            Map<String, Object> geometry = (Map<String, Object>) firstResult.get("geometry");
            Map<String, Object> location = (Map<String, Object>) geometry.get("location");
            Double lat = (Double) location.get("lat");
            Double lng = (Double) location.get("lng");
            return new Coordinates(lat, lng);
        } catch (Exception e) {
            logger.error("Error while translating address: '" + address + "'", e);
            return null;
        }
    }
}
