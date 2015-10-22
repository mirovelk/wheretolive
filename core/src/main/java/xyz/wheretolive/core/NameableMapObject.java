package xyz.wheretolive.core;

public class NameableMapObject extends MapObject implements Nameable {

    private final String name;

    public NameableMapObject(Coordinates location, String name) {
        super(location);
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }
}
