package xyz.wheretolive.crawl.foodMarket.albert;

import java.util.Collection;
import java.util.HashSet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;

@Component
public class AlbertCrawler extends FoodMarketCrawler {

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
            Collection<FoodMarket> toReturn = albertMap.getShopsList();
            
            for (FoodMarket foodMarket : toReturn) {
                foodMarket.setName(ALBERT);
            }

            return new HashSet<>(toReturn);
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    protected String getName() {
        return ALBERT;
    }
}
