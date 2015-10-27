package xyz.wheretolive.crawl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.mongo.MapObjectRepository;

@Component
public class CrawlerProcessor {
    
    @Autowired
    private List<Crawler> crawlers;
    
    @Autowired
    private MapObjectRepository repository;

    public void process() {
        for (Crawler crawler : crawlers) {
            Collection<MapObject> mapObjects = crawler.crawl();
            for (MapObject mapObject : mapObjects) {
                repository.store(mapObject);
            }
        }
    }
}
