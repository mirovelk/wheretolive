package xyz.wheretolive.crawl.billa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.BillaCrawler;
import xyz.wheretolive.crawl.IntegrationTest;

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
