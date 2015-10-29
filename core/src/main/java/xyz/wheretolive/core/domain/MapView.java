package xyz.wheretolive.core.domain;

public class MapView {
    
    private Coordinates topLeft;

    private Coordinates bottomRight;
    
    public MapView() {
        
    }

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

    public void setBottomRight(Coordinates bottomRight) {
        this.bottomRight = bottomRight;
    }

    public void setTopLeft(Coordinates topLeft) {
        this.topLeft = topLeft;
    }
    
    public double getMaxDimension() {
        if (topLeft == null || bottomRight == null) {
            return 0;
        }
        return Math.max(Math.abs(topLeft.getLatitude() - bottomRight.getLatitude()),
                Math.abs(topLeft.getLongitude() - bottomRight.getLongitude()));
    }
}
