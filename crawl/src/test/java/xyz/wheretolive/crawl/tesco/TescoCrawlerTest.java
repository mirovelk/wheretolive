package xyz.wheretolive.crawl.tesco;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.KauflandCrawler;
import xyz.wheretolive.crawl.TescoCrawler;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class TescoCrawlerTest extends IntegrationTest {
    
    @Autowired
    TescoCrawler tescoCrawler;
    
    @Test
    public void test() {
        Collection<MapObject> crawl = tescoCrawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
