package xyz.wheretolive.core.domain;

public class FoodMarket extends NameableMapObject {

    //TODO: type of market
//    public enum Type {
//        SUPERMARKET,
//        HYPERMARKET,
//        EXPRESS;
//    }

    //TODO: opening hours
    
    public FoodMarket(Coordinates location, String name) {
        super(location, name);
    }
    
    public FoodMarket() {
        
    }
}
