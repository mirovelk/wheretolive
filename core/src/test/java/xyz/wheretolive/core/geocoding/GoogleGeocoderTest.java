package xyz.wheretolive.core.geocoding;

import org.junit.Test;
import xyz.wheretolive.core.domain.Coordinates;

import static org.junit.Assert.*;

public class GoogleGeocoderTest {
    
    @Test
    public void test() {
        GoogleGeocoder geocoder = new GoogleGeocoder();
        String address = "Jiraskova 39, Presov, 08005, Slovensko";
        Coordinates coordinates = geocoder.translate(address);
        assertEquals(new Coordinates(48.9765142, 21.2584324), coordinates);
    }
}
