package xyz.wheretolive.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Component;

@Component
public class MongoConnector {

    private final Morphia morphia;
    
    private final Datastore datastore;

    public MongoConnector() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        morphia = new Morphia();
        morphia.mapPackage("xyz.wheretolive.core");
        
        datastore = morphia.createDatastore(mongoClient, "wheretolive");
    }

    protected Datastore getDatastore() {
        return datastore;
    }
    
}
