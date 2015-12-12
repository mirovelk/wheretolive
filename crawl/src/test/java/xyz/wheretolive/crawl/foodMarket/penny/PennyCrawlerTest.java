package xyz.wheretolive.crawl.foodMarket.penny;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;

public class PennyCrawlerTest extends IntegrationTest {
    
    @Autowired
    PennyCrawler pennyCrawler;
    
    @Test
    public void test() {
        Collection<MapObject> crawl = pennyCrawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
