package xyz.wheretolive.core;

public class MapObject implements Localizable {
    
    private final Coordinates location;
    
    public MapObject(Coordinates location) {
        this.location = location;
    }

    @Override
    public Coordinates getLocation() {
        return location;
    }
}
