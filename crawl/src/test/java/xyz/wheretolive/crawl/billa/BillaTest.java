package xyz.wheretolive.crawl.billa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.pageObject.BillaMap;

import java.util.List;

public class BillaTest extends IntegrationTest implements IBilla{

    private Logger logger = LogManager.getLogger(BillaTest.class);

    WebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.get(BILLA_SHOPS_URL);
    }

    @After
    public void teardown() {
        webDriver.quit();
    }

    @Test
    public void getBillaRegionList() {
        BillaMap billaMap = new BillaMap(webDriver);
        List<String> billaRegion = billaMap.getBillaRegion();

        for ( String currentRegion : billaRegion ) {
            billaMap.get(BILLA_SHOPS_URL);
            billaMap.clickRegion(currentRegion);
            List<String> billaShop = billaMap.getAddresses();
        }

    }

}
