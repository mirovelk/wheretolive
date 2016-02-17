package xyz.wheretolive.crawl;

import java.util.Collection;
import java.util.List;

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
            logger.info("Starting execution of " + getName() + " Crawler.");
            Collection<MapObject> result = crawl();
            logger.info("Execution of " + getName() + " Crawler finished.");
            updateWithOldTimestamps(result);
            removeOld();
            store(result);
        } catch (Exception e) {
            logger.error("Error while executing crawler", e);
        }
    }

    private void updateWithOldTimestamps(Collection<MapObject> result) {
        List<? extends NameableMapObject> nameableMapObjects = repository.load(getType(), getName());
        int existingObjectsCount = 0;
        for (MapObject mapObject : result) {
            if (nameableMapObjects.contains(mapObject)) {
                int i = nameableMapObjects.indexOf(mapObject);
                mapObject.setUpdateTime(nameableMapObjects.get(i).getUpdateTime());
                existingObjectsCount++;
            }
        }
        logger.info("Updating " + existingObjectsCount + " objects out of " + result.size() + ". " + (result.size() - existingObjectsCount) + " objects are new.");
    }

    private void removeOld() {
        repository.delete(getType(), getName());
    }
    
    abstract protected Class<? extends NameableMapObject> getType();
}
