package xyz.wheretolive.mongo;

import java.util.Collection;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.NameableMapObject;

public interface MapObjectRepository {
    
    void store(MapObject mapObject);
    
    Collection<MapObject> getIn(MapView view);

    <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type);

    <E extends NameableMapObject> void delete(Class<E> type, String name);
}
