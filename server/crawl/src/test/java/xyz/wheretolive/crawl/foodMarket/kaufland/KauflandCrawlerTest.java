package xyz.wheretolive.crawl.foodMarket.kaufland;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.foodMarket.kaufland.KauflandCrawler;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class KauflandCrawlerTest extends IntegrationTest {
    
    @Autowired
    KauflandCrawler kauflandCrawler;
    
    @Test
    public void test() {
        Collection<MapObject> crawl = kauflandCrawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
