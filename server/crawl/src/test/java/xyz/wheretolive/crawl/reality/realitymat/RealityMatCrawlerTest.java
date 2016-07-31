package xyz.wheretolive.crawl.reality.realitymat;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;

public class RealityMatCrawlerTest extends IntegrationTest {

    @Autowired
    private RealityMatCrawler crawler;

    @Test
    public void test() {
        Collection<MapObject> crawl = crawler.crawl();
        assertTrue(crawl.size() > 0);
    }

}