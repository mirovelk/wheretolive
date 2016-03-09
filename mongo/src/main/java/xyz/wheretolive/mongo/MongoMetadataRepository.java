package xyz.wheretolive.mongo;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xyz.wheretolive.core.domain.CrawlerLog;

@Repository
public class MongoMetadataRepository implements MetadataRepository {

    private DatastoreProvider datastoreProvider;

    @Autowired
    public MongoMetadataRepository(DatastoreProvider datastoreProvider) {
        this.datastoreProvider = datastoreProvider;
    }

    @Override
    public List<CrawlerLog> load() {
        Query<CrawlerLog> query = datastoreProvider.getDatastore().find(CrawlerLog.class);
        return query.asList();
    }

    @Override
    public void persist(CrawlerLog crawlerLog) {
        datastoreProvider.getDatastore().save(crawlerLog);
    }
}
