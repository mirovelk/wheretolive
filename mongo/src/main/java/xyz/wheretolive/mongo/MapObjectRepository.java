package xyz.wheretolive.mongo;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;

import java.util.Collection;

public interface MapObjectRepository {
    
    void store(MapObject mapObject);
    
    Collection<MapObject> getBetween(Coordinates topLeft, Coordinates bottomRight);
}
