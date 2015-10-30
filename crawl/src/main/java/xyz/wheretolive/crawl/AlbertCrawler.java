package xyz.wheretolive.crawl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.AlbertMap;

@Component
public class AlbertCrawler implements Crawler {

    private static Logger logger = LogManager.getLogger(AlbertCrawler.class);
    private static final String ALBERT = "Albert";
    public static final String ALBERT_SHOPS_URL = "http://www.albert.cz/nase-prodejny/mapa-prodejen?region=a&district=&shop_search_tab=shop-search-advanced";

    @Autowired
    GoogleGeocoder geocoder;

    @Override
    public Collection<MapObject> crawl() {
        WebDriver webDriver = null;
        try {
            webDriver = new ChromeDriver();
            webDriver.get(ALBERT_SHOPS_URL);

            AlbertMap albertMap = new AlbertMap(webDriver);
            Collection<MapObject> toReturn = albertMap.getShopsList();

            return toReturn;
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
}
