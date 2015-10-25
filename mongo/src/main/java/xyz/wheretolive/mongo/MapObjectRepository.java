package xyz.wheretolive.mongo;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;

import java.util.Collection;

public interface MapObjectRepository {
    
    void store(MapObject mapObject);
    
    Collection<MapObject> getIn(MapView view);

    <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type);
}
