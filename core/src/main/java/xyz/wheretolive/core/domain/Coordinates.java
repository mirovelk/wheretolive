package xyz.wheretolive.core.domain;

public class Coordinates {

    private double latitude;
    
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates() {
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    @Override
    public int hashCode() {
        return (int) (Math.round(latitude) * 31 + Math.round(longitude));
    }
}
