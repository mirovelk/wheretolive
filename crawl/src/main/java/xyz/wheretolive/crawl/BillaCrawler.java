package xyz.wheretolive.crawl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.BillaMap;

@Component
public class BillaCrawler implements Crawler {

    private static final String BILLA = "Billa";

    public static final String BILLA_SHOPS_URL = "https://www.billa.cz/billa.controls/filialfinder/maps/pinpointv3/pinpoint3.32.html?fx=&dataUrl=https://service.cz.rewe.co.at/filialservice/filialjson.asmx/getfilialenjson?shopcd=%22CZ%22&language=CZE&country=CZ";

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
            Set<String> globalBillaShops = new HashSet<>();

            for (String currentRegion : billaRegion) {
                billaMap.get(BILLA_SHOPS_URL);
                billaMap.clickRegion(currentRegion);
                List<String> billaShop = billaMap.getAddresses();
                globalBillaShops.addAll(billaShop);
            }

            List<MapObject> toReturn = new LinkedList<>();
            for (String currentShopAddress : globalBillaShops) {
                toReturn.add(new FoodMarket(geocoder.translate(currentShopAddress), BILLA, null));
            }

            return toReturn;
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
}
