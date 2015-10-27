package xyz.wheretolive.crawl.geocoding;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.crawl.IntegrationTest;

public class GoogleGeocoderTest extends IntegrationTest {
    
    @Autowired
    GoogleGeocoder geocoder;
    
    @Test
    public void test() {
        String address = "Jiraskova 39, Presov, 08005, Slovensko";
        Coordinates coordinates = geocoder.translate(address);
        assertEquals(new Coordinates(48.9765142, 21.2584324), coordinates);
    }
}
