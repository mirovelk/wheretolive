package xyz.wheretolive.crawl.foodMarket.billa;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import xyz.wheretolive.crawl.IntegrationTest;
import xyz.wheretolive.crawl.foodMarket.billa.BillaCrawler;
import xyz.wheretolive.crawl.foodMarket.billa.BillaMap;

public class BillaTest extends IntegrationTest {

    WebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.get(BillaCrawler.BILLA_SHOPS_URL);
    }

    @After
    public void teardown() {
        webDriver.quit();
    }

    @Test
    public void getBillaRegionList() {
        BillaMap billaMap = new BillaMap(webDriver);
        List<String> billaRegion = billaMap.getBillaRegion();

        for (String currentRegion : billaRegion) {
            billaMap.get(BillaCrawler.BILLA_SHOPS_URL);
            billaMap.clickRegion(currentRegion);
            List<String> billaShop = billaMap.getAddresses();
        }

    }

}
