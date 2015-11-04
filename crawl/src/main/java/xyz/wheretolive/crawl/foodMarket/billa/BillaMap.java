package xyz.wheretolive.crawl.foodMarket.billa;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import xyz.wheretolive.crawl.selenium.WebDriverCrawler;

/**
 * Created by anthonymottot on 25/10/2015.
 */
public class BillaMap extends WebDriverCrawler {

    private Logger logger = LogManager.getLogger(BillaMap.class);

    private static final String BILLA_SHOPS_CONTAINTER = "//div[@id='WW_myList_control_content']";
    //    private static final String ALL_SHOPS = "./div[contains(@class, 'ww_abstract_location_list CZ_')]//table//td[@class='ww_abstract_location_list_table_row_column2']/a[@class='ww_abstract_location_list_table_row_column_address']";
    private static final String ALL_SHOPS = ".//a[@class='ww_abstract_location_list_table_row_column_address']";
    private static final String ADDRESS_LINE1 = ".//div[contains(@class, 'ww_address_line0')]";
    private static final String ADDRESS_LINE3 = ".//div[contains(@class, 'ww_address_line3')]";

    public BillaMap(WebDriver webDriver) {
        super(webDriver);
    }

    public List<String> getBillaRegion() {
        WebElement weBillaShopContainer = getWebElement(BILLA_SHOPS_CONTAINTER);
        List<WebElement> weBillaShopsRegionList = getWebElements(weBillaShopContainer, "./a");
        List<String> billaRegion = new LinkedList<>();

        for (WebElement weCurrent : weBillaShopsRegionList) {
            logger.debug("Region to be treated : " + weCurrent.getText());
            billaRegion.add(weCurrent.getText());
        }

        return billaRegion;
    }

    public BillaMap clickRegion(String region) {
        getWebElement("//a[contains(., '" + region + "')]").click();
        areVisible(ALL_SHOPS);
        return this;
    }

    public List<String> getAddresses() {
        WebElement weBillaShopContainer = getWebElement(BILLA_SHOPS_CONTAINTER);
        List<WebElement> weBillaShopsList = getWebElements(weBillaShopContainer, ALL_SHOPS);
        List<String> billaShop = new LinkedList<>();

        for (WebElement weCurrent : weBillaShopsList) {
            logger.debug(
                    "Shop to be treated : " + getWebElement(weCurrent, ADDRESS_LINE1).getText() + " " + getWebElement(
                            weCurrent, ADDRESS_LINE3).getText());
            billaShop.add(weCurrent.getText());
        }

        return billaShop;
    }

}
