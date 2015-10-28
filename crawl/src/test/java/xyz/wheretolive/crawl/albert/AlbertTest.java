package xyz.wheretolive.crawl.albert;

import java.util.HashSet;
import java.util.Set;

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

/**
 * Created by anthonymottot on 27/10/2015.
 */
public class AlbertTest extends IntegrationTest {

    private Logger logger = LogManager.getLogger(AlbertTest.class);

    WebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.get(AlbertCrawler.ALBERT_SHOPS_URL);
    }

    @After
    public void teardown() {
        webDriver.quit();
    }

    @Test
    public void getAblertRegionList() {
        AlbertMap albertMap = new AlbertMap(webDriver);
        Set<String> albertRegions = albertMap.getRegions();
        Set<FoodMarket> markets = new HashSet<>();

        for (String currentRegion : albertRegions) {
            logger.debug("currentRegion: " + currentRegion);

            albertMap.selectRegion(currentRegion).selectDistrict("-- zvolte okres --").search();
            Set<FoodMarket> foodMarkets = albertMap.extractAddress();
            markets.addAll(foodMarkets);
        }

    }
}
