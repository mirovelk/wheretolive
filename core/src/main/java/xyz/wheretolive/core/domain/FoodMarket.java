package xyz.wheretolive.core.domain;

public class FoodMarket extends NameableMapObject {

    public enum Type {
        SUPERMARKET,
        HYPERMARKET,
        EXPRESS;
    }

    //TODO: opening hours
    
    private Type type;
    
    public FoodMarket(Coordinates location, String name, Type type) {
        super(location, name);
        this.type = type;
    }
    
    public FoodMarket() {
        
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
