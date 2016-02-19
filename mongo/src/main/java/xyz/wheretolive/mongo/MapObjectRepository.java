package xyz.wheretolive.mongo;

import java.util.Collection;
import java.util.List;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.NameableMapObject;
import xyz.wheretolive.core.domain.Reality;

public interface MapObjectRepository {
    
    void store(MapObject mapObject);
    
    <E extends NameableMapObject> List<E> load(Class<E> type, String name);

    Reality loadReality(String realityId, String name);
    
    Collection<MapObject> getIn(MapView view);

    <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type);

    <E extends NameableMapObject> void delete(Class<E> type, String name);
}
