package xyz.wheretolive.core.domain;

public class Reality extends Housing {
    
    private String realityId;

    public Reality(){
    }
    
    public Reality(String realityId, double price, double area, String name, Coordinates location) {
        super(price, area, name, location);
        this.realityId = realityId;
    }

    public String getRealityId() {
        return realityId;
    }

    public void setRealityId(String realityId) {
        this.realityId = realityId;
    }
}
