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

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NameableMapObject)) {
            return false;
        }
        NameableMapObject other = (NameableMapObject) obj;
        return super.equals(obj) && name == null ? other.name == null : name.equals(other.name);
    }
}
