package xyz.wheretolive.crawl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.BillaMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BillaCrawler implements Crawler {
    
    @Override
    public Collection<MapObject> crawl() {

        WebDriver webDriver = new ChromeDriver();

        BillaMap billaMap = new BillaMap(webDriver);
        List<String> billaRegion = billaMap.getBillaRegion();
        List<String> globalBillaShops = new LinkedList<>();
        List<MapObject> toReturn = new LinkedList<>();

        for ( String currentRegion : billaRegion ) {
            billaMap.get(BILLA_SHOPS_URL);
            billaMap.clickRegion(currentRegion);
            List<String> billaShop = billaMap.getAddresses();
            globalBillaShops.addAll(billaShop);
        }

        for ( String currentShopAddress : globalBillaShops ) {
            GoogleGeocoder geocoder = new GoogleGeocoder();
            toReturn.add(new MapObject(geocoder.translate(currentShopAddress)));
        }

        webDriver.close();

        return toReturn;
    }
}
