package xyz.wheretolive.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.wheretolive.core.geocoding.GoogleGeocodeResult;

@Repository
public class MongoGoogleGeocodeRepository implements GoogleGeocodeRepository {

    private DatastoreProvider datastoreProvider;

    @Autowired
    public MongoGoogleGeocodeRepository(DatastoreProvider datastoreProvider) {
        this.datastoreProvider = datastoreProvider;
    }


    @Override
    public void store(GoogleGeocodeResult result) {
        datastoreProvider.getDatastore().save(result);
    }

    @Override
    public GoogleGeocodeResult find(String query) {
        return datastoreProvider.getDatastore().find(GoogleGeocodeResult.class, "query", query).get();
    }
}
