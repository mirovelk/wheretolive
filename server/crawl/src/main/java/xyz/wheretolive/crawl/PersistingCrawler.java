package xyz.wheretolive.crawl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.CrawlerLog;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.NameableMapObject;
import xyz.wheretolive.mongo.MapObjectRepository;
import xyz.wheretolive.mongo.MetadataRepository;

public abstract class PersistingCrawler implements Crawler {

    protected static Logger logger = LogManager.getLogger(PersistingCrawler.class);

    @Autowired
    private MapObjectRepository repository;

    @Autowired
    private MetadataRepository metadataRepository;

    private void store(Collection<MapObject> mapObjects) {
        for (MapObject mapObject : mapObjects) {
            repository.store(mapObject);
        }
    }
    
    @Override
    public void execute() {
        String message;
        Date start = new Date();
        try {
            logger.info("Starting execution of " + getName() + " Crawler.");
            Collection<MapObject> result = crawl();
            logger.info("Execution of " + getName() + " Crawler finished.");
            message = updateWithOldTimestamps(result);
            logger.info(message);
            removeOld();
            store(result);
        } catch (Exception e) {
            logger.error("Error while executing crawler", e);
            message = e.getMessage();
        }
        Date end = new Date();
        int executionTimeInSeconds = (int) ((end.getTime() - start.getTime()) / 1000);
        storeResultInMetadata(executionTimeInSeconds, message, start);
    }

    private void storeResultInMetadata(int executionTimeInSeconds, String message, Date start) {
        CrawlerLog crawlerLog = new CrawlerLog();
        crawlerLog.setCrawlerName(getName());
        crawlerLog.setExecutionTimeInSeconds(executionTimeInSeconds);
        crawlerLog.setMessage(message);
        crawlerLog.setTimestamp(start);
        metadataRepository.persist(crawlerLog);
    }

    private String updateWithOldTimestamps(Collection<MapObject> result) {
        List<? extends NameableMapObject> existingMapObjects = repository.load(getType(), getName());
        int existingObjectsCount = 0;
        for (MapObject mapObject : result) {
            if (existingMapObjects.contains(mapObject)) {
                int i = existingMapObjects.indexOf(mapObject);
                mapObject.setUpdateTime(existingMapObjects.get(i).getUpdateTime());
                existingObjectsCount++;
            }
        }
        return "Total objects: " + result.size() + ". \tExisting objects: " + existingObjectsCount + ". \tNew objects: " + (result.size() - existingObjectsCount) + ".";
    }

    private void removeOld() {
        repository.delete(getType(), getName());
    }
    
    abstract protected Class<? extends NameableMapObject> getType();
}
