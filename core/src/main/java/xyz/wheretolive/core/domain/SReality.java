package xyz.wheretolive.core.domain;

public class SReality extends Housing {

    private String sRealityId;

    public SReality(){
    }

    public SReality(String sRealityId, double price, double area, Coordinates location) {
        super(price, area, location);
        this.sRealityId = sRealityId;
    }

    public String getsRealityId() {
        return sRealityId;
    }

    public void setsRealityId(String sRealityId) {
        this.sRealityId = sRealityId;
    }

}
