package xyz.wheretolive.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;

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
    public Collection<MapObject> getBetween(Coordinates topLeft, Coordinates bottomRight) {
        return datastoreProvider.getDatastore().createQuery(MapObject.class).asList();
    }
}
