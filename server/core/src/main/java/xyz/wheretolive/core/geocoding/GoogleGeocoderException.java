package xyz.wheretolive.core.geocoding;

public class GoogleGeocoderException extends RuntimeException {
    
    public GoogleGeocoderException(String s) {
        super(s);
    }

    public GoogleGeocoderException(String message, Exception e) {
        super(message, e);
    }
}
