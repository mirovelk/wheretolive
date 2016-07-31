package xyz.wheretolive.crawl.geocoding;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.geocoding.GoogleGeocodeResult;
import xyz.wheretolive.crawl.IntegrationTest;

import java.util.UUID;

public class GoogleGeocoderTest extends IntegrationTest {
    
    @Autowired
    private GoogleGeocoder geocoder;
    
    @Test
    public void geocoderReturnsCorrectCoordinates() throws Exception {
        String address = "Jiraskova 39, Presov, 08005, Slovensko";
        address = geocoder.normalizeAddress(address);
        GoogleGeocodeResult result = geocoder.query(address);
        Coordinates coordinates = geocoder.extract(result);
        assertEquals(new Coordinates(48.9765142, 21.2584324), coordinates);
    }

    @Test
    public void gettingNewAddressStoresTheResultToCache() {
        String randomString = UUID.randomUUID().toString();
        try {
            geocoder.translate(randomString);
        } catch (Exception e) {

        }
        GoogleGeocodeResult fromCache = geocoder.getFromCache(randomString);
        assertNotNull(fromCache);
    }
}
