package xyz.wheretolive.core.domain;

public class RealityId {

    private String name;

    private String id;
    
    public RealityId(String name, String id) {
        this.name = name;
        this.id = id;
    }
    
    public RealityId() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + "_" + id;
    }
}
