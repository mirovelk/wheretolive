package xyz.wheretolive.crawl.transport.pid;

import java.util.ArrayList;
import java.util.Collection;

import xyz.wheretolive.core.domain.MapObject;

public class DeprecatedPIDCrawler extends PIDCrawler {

    @Override
    public Collection<MapObject> crawl() {
        return new ArrayList<>();
    }
}
