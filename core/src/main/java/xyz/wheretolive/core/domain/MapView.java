package xyz.wheretolive.core.domain;

public class MapView {
    
    private final Coordinates topLeft;

    private final Coordinates bottomRight;

    public MapView(Coordinates topLeft, Coordinates bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Coordinates getBottomRight() {
        return bottomRight;
    }

    public Coordinates getTopLeft() {
        return topLeft;
    }
    
    public double getMaxDimension() {
        return Math.max(Math.abs(topLeft.getLatitude() - bottomRight.getLatitude()),
                Math.abs(topLeft.getLongitude() - bottomRight.getLongitude()));
    }
}
