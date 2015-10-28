package xyz.wheretolive.crawl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CrawlerProcessorTest extends IntegrationTest {
    
    @Autowired
    private CrawlerProcessor processor;
    
    @Test
    public void test() {
        processor.process();
    }

}
