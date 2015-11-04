package xyz.wheretolive.crawl;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.NameableMapObject;
import xyz.wheretolive.mongo.MapObjectRepository;

public abstract class PersistingCrawler implements Crawler {

    private static Logger logger = LogManager.getLogger(PersistingCrawler.class);

    @Autowired
    private MapObjectRepository repository;

    private void store(Collection<MapObject> mapObjects) {
        for (MapObject mapObject : mapObjects) {
            repository.store(mapObject);
        }
    }
    
    @Override
    public void execute() {
        try {
            Collection<MapObject> result = crawl();
            removeOld();
            store(result);
        } catch (Exception e) {
            logger.error("Error while executing crawler", e);
        }
    }

    private void removeOld() {
        repository.delete(getType(), getName());
    }
    
    abstract protected String getName();

    abstract protected Class<? extends NameableMapObject> getType();
}
