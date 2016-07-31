package xyz.wheretolive.mongo;

import xyz.wheretolive.core.geocoding.GoogleGeocodeResult;

public interface GoogleGeocodeRepository {
    
    void store(GoogleGeocodeResult result);
    
    GoogleGeocodeResult find(String query);
}
