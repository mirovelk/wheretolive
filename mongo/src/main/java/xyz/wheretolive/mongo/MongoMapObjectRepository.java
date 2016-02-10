package xyz.wheretolive.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.NameableMapObject;

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
    public <E extends NameableMapObject> List<E> load(Class<E> type, String name) {
        Query<E> query = datastoreProvider.getDatastore().find(type, "name", name);
        return query.asList();
    }

    @Override
    public Collection<MapObject> getIn(MapView view) {
        return datastoreProvider.getDatastore()
                .createQuery(MapObject.class)
                .filter("location.latitude >", view.getSouthWest().getLatitude())
                .filter("location.longitude >", view.getSouthWest().getLongitude())
                .filter("location.latitude <", view.getNorthEast().getLatitude())
                .filter("location.longitude <", view.getNorthEast().getLongitude())
                .asList();
    }

    @Override
    public <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type) {
        Collection<MapObject> mapObjects = getIn(view);
        Collection<E> result = new ArrayList<>();
        for (MapObject mo : mapObjects) {
            if (type.isAssignableFrom(mo.getClass())) {
                result.add((E) mo);
            }
        }
        return result;
    }

    @Override
    public <E extends NameableMapObject> void delete(Class<E> type, String name) {
        datastoreProvider.getDatastore().delete(datastoreProvider.getDatastore().createQuery(type).filter("name", name));
    }
}
