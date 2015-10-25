package xyz.wheretolive.crawl.billa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wheretolive.crawl.pageObject.BillaMap;
import xyz.wheretolive.crawl.pageObject.IBillaMap;
import xyz.wheretolive.mongo.SpringConfig;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class BillaTest implements IBilla {

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
