package xyz.wheretolive.crawl.reality.sreality;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;

public class SRealityCrawlerTest extends IntegrationTest {

    @Autowired
    private SRealityCrawler crawler;

    @Test
    public void test() {
        Collection<MapObject> crawl = crawler.crawl();
        assertTrue(crawl.size() > 0);
    }

}
