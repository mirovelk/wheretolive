package xyz.wheretolive.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class MongoMapObjectRepository implements MapObjectRepository {
    
    private DatastoreProvider datastoreProvider;

    @Autowired
    public MongoMapObjectRepository(DatastoreProvider datastoreProvider) {
        this.datastoreProvider = datastoreProvider;
    }

    @Override
    public void store(MapObject mapObject) {
        datastoreProvider.getDatastore().save(mapObject);
    }

    @Override
    public Collection<MapObject> getIn(MapView view) {
        return datastoreProvider.getDatastore().createQuery(MapObject.class).asList();
    }

    @Override
    public <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type) {
        Collection<MapObject> mapObjects = getIn(view);
        Collection<E> result = new ArrayList<>();
        for (MapObject mo : mapObjects) {
            if (mo.getClass() == type) {
                result.add((E) mo);
            }
        }
        return result;
    }
}
