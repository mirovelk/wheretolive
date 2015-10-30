package xyz.wheretolive.crawl.pageObject;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.selenium.WebDriverCrawler;

public class AlbertMap extends WebDriverCrawler {

    private Logger logger = LogManager.getLogger(AlbertMap.class);

    public AlbertMap(WebDriver webDriver) {
        super(webDriver);
    }

    public Collection<MapObject> getShopsList() {
        Collection<MapObject> toReturn = new LinkedHashSet<>();

        WebElement weScript = webDriver.findElement(By.xpath("//div[@class='text-pane' and descendant::ul[@class='tabular-list']]/following-sibling::script"));
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        String json = (String) jse.executeScript("return arguments[0].text", weScript);
        json = json.replace("todo[todo.length] = function() {", "")
                .replace("new AHPage.ShopMap(", "")
                .replace(", {", "")
                .replace("height: 232,", "")
                .replace("icons: {", "")
                .replace("hypermarket: '/-a5673?field=data',", "")
                .replace("supermarket: '/-a5672?field=data'", "")
                .replace("},", "")
                .replace("parent: $('#search-results-map')", "")
                .replace("});", "")
                .replace("};", "")
                .trim();

        logger.debug(json);

        Gson gson = new Gson();
        gson = new GsonBuilder().create();
        List<AlbertObject> albertObjectList = gson.fromJson(json, new TypeToken<List<AlbertObject>>(){}.getType());
        for ( AlbertObject currentAlbert : albertObjectList) {
            logger.debug("geo localization for "+ currentAlbert.getTitle() + " : " + currentAlbert.getLatitude() + ", " + currentAlbert.getLongitude());
            Coordinates coordinates = new Coordinates(currentAlbert.getLatitude(), currentAlbert.getLongitude());
            toReturn.add(new FoodMarket(coordinates, currentAlbert.getTitle()));
        }

        return toReturn;
    }
}