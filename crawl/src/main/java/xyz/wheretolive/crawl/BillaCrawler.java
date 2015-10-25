package xyz.wheretolive.crawl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.BillaMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
public class BillaCrawler implements Crawler {
    
    @Autowired
    GoogleGeocoder geocoder;
    
    @Override
    public Collection<MapObject> crawl() {
        WebDriver webDriver = null;
        try {
            webDriver = new ChromeDriver();
            webDriver.get(BILLA_SHOPS_URL);

            BillaMap billaMap = new BillaMap(webDriver);
            List<String> billaRegion = billaMap.getBillaRegion();
            List<String> globalBillaShops = new LinkedList<>();
            List<MapObject> toReturn = new LinkedList<>();

            for (String currentRegion : billaRegion) {
                billaMap.get(BILLA_SHOPS_URL);
                billaMap.clickRegion(currentRegion);
                List<String> billaShop = billaMap.getAddresses();
                globalBillaShops.addAll(billaShop);
            }

            for (String currentShopAddress : globalBillaShops) {
                toReturn.add(new MapObject(geocoder.translate(currentShopAddress)));
            }
            
            return toReturn;
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
}
