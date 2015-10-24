package xyz.wheretolive.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.wheretolive.core.domain.MapObject;

@Repository
public class MongoMapObjectRepository implements MapObjectRepository {
    
    private MongoConnector connector;

    @Autowired
    public MongoMapObjectRepository(MongoConnector connector) {
        this.connector = connector;
    }

    @Override
    public void store(MapObject mapObject) {
        connector.getDatastore().save(mapObject);
    }
}
