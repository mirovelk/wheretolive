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

    @Override
    public int hashCode() {
        return realityId == null ? 0 : realityId.hashCode() * 31 + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Reality)) {
            return false;
        }
        Reality other = (Reality) obj;
        return super.equals(obj) && realityId == null ? other.realityId == null : realityId.equals(other.realityId);
    }
}
