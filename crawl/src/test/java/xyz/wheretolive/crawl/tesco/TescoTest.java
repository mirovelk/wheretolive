package xyz.wheretolive.crawl.tesco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.crawl.AlbertCrawler;
import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.pageObject.AlbertMap;
import xyz.wheretolive.crawl.pageObject.TescoMap;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by anthonymottot on 27/10/2015.
 */
public class TescoTest extends IntegrationTest {

    private Logger logger = LogManager.getLogger(TescoTest.class);

    WebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
//        webDriver.get(TescoCrawler.TESCO_SHOPS_URL);
        webDriver.get("http://www.itesco.cz/cs/#m");
    }

    @After
    public void teardown() {
        webDriver.quit();
    }

    @Test
    public void getTescoList() {
        TescoMap tescoMap = new TescoMap(webDriver);
        Set<FoodMarket> markets = tescoMap.getShopsList();
    }
}
