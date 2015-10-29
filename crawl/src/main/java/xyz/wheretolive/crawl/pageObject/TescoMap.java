package xyz.wheretolive.crawl.pageObject;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.crawl.selenium.WebDriverCrawler;

import java.util.*;

import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by anthonymottot on 29/10/2015.
 */
public class TescoMap extends WebDriverCrawler implements ITescoMap {

    private Logger logger = LogManager.getLogger(BillaMap.class);

    @FindBy ( xpath = SHOPS_CONTAINER )
    WebElement weShopsContainer;

    public TescoMap(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @Override
    public Set<FoodMarket> getShopsList() {
        Set<FoodMarket> toReturn = new HashSet<>();
        List<WebElement> shopItems = weShopsContainer.findElements(By.xpath(SHOPS_ITEMS));
        int position = 1;
        for ( WebElement currentShop : shopItems ) {
            moveTo("(//div[@id='store-list']//div[@class='store-item'])[" + position + "]");
            String name = currentShop.findElement(By.xpath(SHOP_NAME)).getText();
//            logger.debug("name: " + name);
            String address = currentShop.findElement(By.xpath(SHOP_ADDRESS)).getText();
            String url = "http://www.itesco.cz/cs/obchody-tesco/" + format2url(name) + "/";

            JavascriptExecutor jse = (JavascriptExecutor) webDriver;
            jse.executeScript("window.open()");

            await().until(new Tab(webDriver), equalTo(2));
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1));
            webDriver.get(url);
//            Coordinates coordinates = (new TescoDetails(webDriver)).getCoordinates();
            if ( webDriver.getCurrentUrl().equals("http://www.itesco.cz/cs/") )
                logger.debug("to check: " + name);
            webDriver.close();
            await().until(new Tab(webDriver), equalTo(1));
            ArrayList<String> tabs1 = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs1.get(0));

//            if (coordinates != null)
//                toReturn.add(new FoodMarket(coordinates, name)));

            position++;
        }

        return toReturn;
    }

    private String format2url(String name) {
        return name.toLowerCase()
                    .replace(" ", "-")
                    .replace("š", "s")
                    .replace("č", "c")
                    .replace("í", "i")
                    .replace("ř", "r")
                    .replace("ň", "n")
                    .replace("á", "a")
                    .replace("ý", "y")
                    .replace("ě", "e")
                    .replace("é", "e")
                    .replace("ž", "z")
                    .replace("ú", "u")
                    .replace("Ú", "u")
                    .replace("ů", "u")
                    .replace("&", "-")
                    .replace(".", "")
                    .replace("---", "-");
    }

// Case that we need to use geolocalization
//Hypermarket Ústí nad Labem
//Supermarket Kraslice
//Supermarket Teplice
//Expres Havířov 17. listopadu
//Expres Příbor Místecká
//Expres Dolní Lutyně Koperníkova
//Supermarket Přerov Čechova
//Expres Nový Jičín Sportovní
//Expres Karviná Borovského
}
