package xyz.wheretolive.crawl;

import java.util.Collection;

import xyz.wheretolive.core.domain.MapObject;

public interface Crawler {

    Collection<MapObject> crawl();

}
