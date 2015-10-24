package xyz.wheretolive.crawl;

import xyz.wheretolive.core.domain.MapObject;
import java.util.Collection;

public interface Crawler {
    
    Collection<MapObject> crawl();
}
