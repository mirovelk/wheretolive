package xyz.wheretolive.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import xyz.wheretolive.core.SpringConfig;
import xyz.wheretolive.core.domain.MapObject;

@ContextConfiguration(classes = {SpringConfig.class})
public class MongoTest {
    
    @Autowired
    DatastoreProvider datastoreProvider;
    
    public void before() {
        datastoreProvider.getDatastore().getCollection(MapObject.class).drop();
    }
}
