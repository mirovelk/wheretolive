package xyz.wheretolive.crawl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wheretolive.core.domain.MapObject;

import java.util.Collection;
import static org.junit.Assert.*;

public class BillaCrawlerTest extends IntegrationTest {
    
    @Autowired
    BillaCrawler billaCrawler;
    
    @Test
    public void test() {
        Collection<MapObject> crawl = billaCrawler.crawl();
        assertTrue(crawl.size() > 0);
    }
}
