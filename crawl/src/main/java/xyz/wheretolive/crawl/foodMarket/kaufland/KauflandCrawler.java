package xyz.wheretolive.crawl.foodMarket.kaufland;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.foodMarket.FoodMarketCrawler;
import xyz.wheretolive.crawl.geocoding.GoogleGeocoder;

@Component
public class KauflandCrawler extends FoodMarketCrawler {

    private static final String KAUFLAND = "Kaufland";

    public static final String KAUFLAND_MOBILE = "http://m.kaufland.cz/Home/index.jsp?checkdevice=false&et_cid=2&et_lid=4";

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
            Set<Coordinates> coordinates = new HashSet<>();

            for (int i = 0; i < kauflandItems.size(); i++) {
                Coordinates currentCoordinates = kauflandMap.getGoogleCoordinatesFromScript(kauflandItems.get(i));
                if (currentCoordinates != null)
                    coordinates.add(currentCoordinates);
            }

            List<MapObject> toReturn = new LinkedList<>();
            for (Coordinates coord : coordinates) {
                toReturn.add(new FoodMarket(coord, KAUFLAND, null));
            }

            return toReturn;
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
        return KAUFLAND;
    }
}
