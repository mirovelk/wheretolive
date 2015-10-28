package xyz.wheretolive.crawl.pageObject;

import static com.jayway.awaitility.Awaitility.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.crawl.selenium.WebDriverCrawler;

public class AlbertMap extends WebDriverCrawler implements IAlbertMap {

    private Logger logger = LogManager.getLogger(AlbertMap.class);

    @FindBy(xpath = SELECT_REGION)
    WebElement weSelectRegion;

    @FindBy(xpath = SELECT_DISTRICT)
    WebElement weSelectDistrict;

    @FindBy(xpath = BUTTON_SEARCH)
    WebElement weSearch;

    @FindBy(xpath = UL_LIST_SHOP)
    WebElement weShopsContainer;

    public AlbertMap(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public Set<String> getRegions() {
        Set<String> toReturn = new LinkedHashSet<>();
        Select select = new Select(weSelectRegion);
        for (WebElement currentOption : select.getOptions()) {
            toReturn.add(currentOption.getText());
        }
        toReturn.remove("-- všechny --");
        return toReturn;
    }

    public AlbertMap selectRegion(String region) {
        weSelectRegion.sendKeys(region);
        return this;
    }

    public AlbertMap selectDistrict(String district) {
//        Select select = new Select(weSelectDistrict);
//        select.selectByValue((district == null ? "-- zvolte okres --" : district));
        weSelectDistrict.sendKeys(district);
        return this;
    }

    public AlbertMap search() {
        isVisible(BUTTON_SEARCH, true);
        weSearch.click();
        return this;
    }

    public Set<FoodMarket> extractAddress() {
        Set<FoodMarket> toReturn = new LinkedHashSet<>();
        List<WebElement> liList = weShopsContainer.findElements(By.xpath("./li"));
        for (WebElement currentShop : liList) {

            WebElement currentUrlShop = currentShop.findElement(By.xpath(".//a[@class='clickable-more']"));
            logger.debug("Name: " + currentUrlShop.getText());

            String operatingSystem = System.getProperty("os.name").toLowerCase();
            if (operatingSystem.contains("win"))
                currentUrlShop.sendKeys(Keys.CONTROL +"t"); // for non Mac
            else if (operatingSystem.contains("mac"))
                currentUrlShop.sendKeys(Keys.chord(Keys.COMMAND, Keys.ENTER)); // for Mac
            else
                logger.debug("Test can't be run on your current system.");

            await().until(new Tab(webDriver), equalTo(2));
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(1));
            Coordinates coordinates = (new AlbertDetails(webDriver)).getCoordinates();
            webDriver.close();
            await().until(new Tab(webDriver), equalTo(1));
            ArrayList<String> tabs1 = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs1.get(0));

            if (coordinates != null)
                toReturn.add(new FoodMarket(coordinates, currentUrlShop.getText()));
        }

        return toReturn;
    }
}

class Tab implements Callable<Integer> {

    WebDriver webDriver;

    Tab(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public Integer call() throws Exception {
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        return tabs.size();
    }
}
