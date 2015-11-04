package xyz.wheretolive.crawl.foodMarket.albert;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.foodMarket.albert.AlbertCrawler;

public class AlbertCrawlerTest extends IntegrationTest {
    
    @Autowired
    AlbertCrawler albertCrawler;
    
    @Test
    public void test() {
        Collection<MapObject> crawl = albertCrawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
