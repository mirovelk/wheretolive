package xyz.wheretolive.crawl.pageObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.crawl.selenium.WebDriverCrawler;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anthonymottot on 25/10/2015.
 */
public class KauflandMap extends WebDriverCrawler implements IKauflandMap {

    private Logger logger = LogManager.getLogger(KauflandMap.class);

    public KauflandMap(WebDriver webDriver) {
        super(webDriver);
    }

    public KauflandMap search(String criteria) {
        isVisible(SEARCH, true);
        WebElement weSearch = getWebElement(SEARCH);
        weSearch.clear();
        weSearch.sendKeys(criteria);
        WebElement weDoSearch = getWebElement(DO_SEARCH);
        weDoSearch.click();
        return this;
    }

    public KauflandMap loadAllResults() {

        while ( isVisible(NEXT_PAGE) ) {
            areVisible(STORE_ITEM);
            getWebElement(NEXT_PAGE).click();
        }

        isInvisible(NEXT_PAGE, 5);
        return this;
    }

    public List<WebElement> getResults() {
        WebElement weContainer = getWebElement(RESULT_CONTAINER);
        List<WebElement> weItems = getWebElements(weContainer, STORE_ITEM);
        return weItems;
    }

    public String getCurrentItemsAddress(WebElement weCurrentItems) {
        return weCurrentItems.findElement(By.xpath("./span[@class='msf-storelist-item-text']/span")).getText().replaceAll("<br>", "").trim();
    }

    public Coordinates getGoogleUrlDetails(WebElement weCurrentItems) {
        Coordinates toReturn = null;
        Pattern pattern = Pattern.compile("^.*coords=(.*)&stores.*$");
        Matcher matcher = pattern.matcher(weCurrentItems.getAttribute("href"));
        String result = null;
        while ( matcher.find() )
            result = matcher.group(1);
        logger.debug("Google coordinate : " + result);
        String[] futureCoordinates = result.split(",");
        toReturn = new Coordinates(Double.parseDouble(futureCoordinates[0]), Double.parseDouble(futureCoordinates[1]));
        return toReturn;
    }

    public Coordinates getGoogleCoordinatesFromScript(WebElement weCurrentItems) {
        Coordinates toReturn = null;
        try {
            JavascriptExecutor jse = (JavascriptExecutor) webDriver;
            WebElement element = weCurrentItems.findElement(By.xpath("./following-sibling::script"));
            String googleMarker = (String) jse.executeScript("return arguments[0].text", element);
            googleMarker = googleMarker.replaceAll("^\\s+|\\s+$|\\s*(\n)\\s*|(\\s)\\s*", "$1$2").replace("\t", "");
            googleMarker = googleMarker.trim();

            Pattern pattern = Pattern.compile("^googlemap\\.addMarker\\(new Marker\\(([\\d]+\\.[\\d]*), ([\\d]+\\.[\\d]*).*$");
            Matcher matcher = pattern.matcher(googleMarker);
            String altitude = null;
            String longitude = null;

            while ( matcher.find() ) {
                altitude = matcher.group(1);
                longitude = matcher.group(2);
            }
            logger.debug("Google coordinate : " + altitude + ", " + longitude);
            toReturn = new Coordinates(Double.parseDouble(altitude), Double.parseDouble(longitude));
        }
        catch( NoSuchElementException nsee ) {
        }
        finally {
            return toReturn;
        }
    }

}
