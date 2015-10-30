package xyz.wheretolive.core.domain;

public class NameableMapObject extends MapObject implements Nameable {

    private String name;

    public NameableMapObject(Coordinates location, String name) {
        super(location);
        this.name = name;
    }

    public NameableMapObject() {
        
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
