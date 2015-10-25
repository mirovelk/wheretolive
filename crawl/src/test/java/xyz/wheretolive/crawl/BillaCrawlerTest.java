package xyz.wheretolive.crawl;

import org.junit.Test;
import xyz.wheretolive.core.domain.MapObject;

import java.util.Collection;
import static org.junit.Assert.*;

public class BillaCrawlerTest {
    
    @Test
    public void test() {
        BillaCrawler crawler = new BillaCrawler();
        Collection<MapObject> crawl = crawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
