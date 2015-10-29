package xyz.wheretolive.crawl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.TescoMap;

@Component
public class TescoCrawler implements Crawler {

    private static final String TESCO = "Tesco";

    @Autowired
    GoogleGeocoder geocoder;

    @Override
    public Collection<MapObject> crawl() {
        WebDriver webDriver = null;
        try {
            webDriver = new ChromeDriver();
            webDriver.get("http://www.itesco.cz/cs/#m");

            TescoMap tescoMap = new TescoMap(webDriver);

            Set<FoodMarket> shopsList = tescoMap.getShopsList();

            return new HashSet<>(shopsList);
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
}
