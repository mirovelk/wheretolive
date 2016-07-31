package xyz.wheretolive.crawl.reality.mm;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;

public class MMCrawlerTest extends IntegrationTest {

    @Autowired
    private MMCrawler crawler;

    @Test
    public void test() {
        Collection<MapObject> crawl = crawler.crawl();
        assertTrue(crawl.size() > 0);
    }

}