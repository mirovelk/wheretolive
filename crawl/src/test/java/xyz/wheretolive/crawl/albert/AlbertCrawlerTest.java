package xyz.wheretolive.crawl.albert;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.AlbertCrawler;
import xyz.wheretolive.crawl.IntegrationTest;

public class AlbertCrawlerTest extends IntegrationTest {
    
    @Autowired
    AlbertCrawler albertCrawler;
    
    @Test
    public void test() {
        Collection<MapObject> crawl = albertCrawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
