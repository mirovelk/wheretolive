package xyz.wheretolive.mongo;

import org.mongodb.morphia.Datastore;

public interface DatastoreProvider {

    Datastore getDatastore();
}
