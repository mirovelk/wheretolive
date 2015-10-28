package xyz.wheretolive.crawl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.AlbertMap;

@Component
public class AlbertCrawler implements Crawler {

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
            Set<String> albertRegions = albertMap.getRegions();

            Collection<MapObject> toReturn = new ArrayList<>();
            
            for (String currentRegion : albertRegions) {
                albertMap.selectRegion(currentRegion).selectDistrict("-- zvolte okres --").search();
                toReturn.addAll(albertMap.extractAddress());
            }

            return toReturn;
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
}
