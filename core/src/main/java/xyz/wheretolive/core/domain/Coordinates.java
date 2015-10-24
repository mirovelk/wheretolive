package xyz.wheretolive.core.domain;

public class Coordinates {
    
    private final double latitude;
    
    private final double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Coordinates)) {
            return false;
        }
        Coordinates c = (Coordinates) obj;
        return c.latitude == latitude && c.longitude == longitude;
    }
}
