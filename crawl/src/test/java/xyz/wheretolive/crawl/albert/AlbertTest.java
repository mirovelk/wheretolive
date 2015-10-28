package xyz.wheretolive.crawl.albert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.pageObject.AlbertMap;
import xyz.wheretolive.crawl.pageObject.BillaMap;

import java.util.List;
import java.util.Set;

/**
 * Created by anthonymottot on 27/10/2015.
 */
public class AlbertTest extends IntegrationTest implements IAlbert {

    private Logger logger = LogManager.getLogger(AlbertTest.class);

    WebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.get(ALBERT_SHOPS_URL);
    }

    @After
    public void teardown() {
        webDriver.quit();
    }

    @Test
    public void getBillaRegionList() {
        AlbertMap albertMap = new AlbertMap(webDriver);
        Set<String> albertRegions = albertMap.getRegions();

        for ( String currentRegion : albertRegions ) {
            logger.debug("currentRegion: " + currentRegion);

            albertMap.selectRegion(currentRegion).selectDistrict("-- zvolte okres --").search();
            albertMap.extractAddress();
        }

    }
}
