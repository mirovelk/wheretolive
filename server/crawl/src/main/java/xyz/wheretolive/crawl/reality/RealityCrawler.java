package xyz.wheretolive.crawl.reality;

import xyz.wheretolive.core.domain.NameableMapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.PersistingCrawler;

public abstract class RealityCrawler extends PersistingCrawler {

    @Override
    protected Class<? extends NameableMapObject> getType() {
        return Reality.class;
    }
}
