package xyz.wheretolive.core.domain;

public class BezRealitky extends Housing {
    
    private String bezRealitkyId;

    public BezRealitky(){
    }
    
    public BezRealitky(String bezRealitkyId, double price, double area, Coordinates location) {
        super(price, area, location);
        this.bezRealitkyId = bezRealitkyId;
    }

    public String getBezRealitkyId() {
        return bezRealitkyId;
    }

    public void setBezRealitkyId(String bezRealitkyId) {
        this.bezRealitkyId = bezRealitkyId;
    }
}
