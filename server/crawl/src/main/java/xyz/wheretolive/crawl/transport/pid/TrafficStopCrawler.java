package xyz.wheretolive.crawl.transport.pid;

import xyz.wheretolive.core.domain.NameableMapObject;
import xyz.wheretolive.core.domain.TrafficStop;
import xyz.wheretolive.crawl.PersistingCrawler;

public abstract class TrafficStopCrawler extends PersistingCrawler {

    @Override
    protected Class<? extends NameableMapObject> getType() {
        return TrafficStop.class;
    }

}
