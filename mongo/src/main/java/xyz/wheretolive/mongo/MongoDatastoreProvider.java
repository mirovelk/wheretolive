package xyz.wheretolive.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Component;

@Component
public class MongoDatastoreProvider implements DatastoreProvider {

    private final Morphia morphia;
    
    private final Datastore datastore;

    public MongoDatastoreProvider() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        morphia = new Morphia();
        morphia.mapPackage("xyz.wheretolive.core");
        
        datastore = morphia.createDatastore(mongoClient, "wheretolive");
    }

    public Datastore getDatastore() {
        return datastore;
    }
    
}
