package xyz.wheretolive.core.domain;

public class MapView {
    
    private Coordinates northEast;

    private Coordinates southWest;
    
    public MapView() {
        
    }

    public MapView(Coordinates northEast, Coordinates southWest) {
        this.northEast = northEast;
        this.southWest = southWest;
    }

    public Coordinates getSouthWest() {
        return southWest;
    }

    public Coordinates getNorthEast() {
        return northEast;
    }

    public void setSouthWest(Coordinates southWest) {
        this.southWest = southWest;
    }

    public void setNorthEast(Coordinates northEast) {
        this.northEast = northEast;
    }
    
    public double getMaxDimension() {
        if (northEast == null || southWest == null) {
            return 0;
        }
        return Math.max(Math.abs(northEast.getLatitude() - southWest.getLatitude()),
                Math.abs(northEast.getLongitude() - southWest.getLongitude()));
    }
}
