package xyz.wheretolive.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class MongoConnector {

    private final MongoDatabase database;
    
    public MongoConnector() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("wheretolive");
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
