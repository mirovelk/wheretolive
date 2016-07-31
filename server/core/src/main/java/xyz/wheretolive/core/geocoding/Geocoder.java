package xyz.wheretolive.core.geocoding;

import xyz.wheretolive.core.domain.Coordinates;

public interface Geocoder {
    
    Coordinates translate(String address);
}
