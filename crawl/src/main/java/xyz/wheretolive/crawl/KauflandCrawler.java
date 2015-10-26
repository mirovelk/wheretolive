package xyz.wheretolive.crawl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;
import xyz.wheretolive.crawl.pageObject.BillaMap;
import xyz.wheretolive.crawl.pageObject.KauflandMap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
public class KauflandCrawler implements Crawler {
    
    @Autowired
    GoogleGeocoder geocoder;
    
    @Override
    public Collection<MapObject> crawl() {
        WebDriver webDriver = null;
        try {
            webDriver = new ChromeDriver();
            webDriver.get(KAUFLAND_MOBILE);

            KauflandMap kauflandMap = new KauflandMap(webDriver);
            kauflandMap
                    .search("česká republika")
                    .loadAllResults();

            List<WebElement> kauflandItems = kauflandMap.getResults();
            List<MapObject> toReturn = new LinkedList<>();

            for ( int i = 0; i < kauflandItems.size(); i++) {
                Coordinates currentCoordinates = kauflandMap.getGoogleCoordinatesFromScript(kauflandItems.get(i));
                if ( currentCoordinates != null)
                    toReturn.add(new MapObject(currentCoordinates));
            }
            
            return toReturn;
        } finally {
            if (webDriver != null) {
                webDriver.close();
            }
        }
    }
}
