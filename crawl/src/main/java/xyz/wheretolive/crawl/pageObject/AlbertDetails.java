package xyz.wheretolive.crawl.pageObject;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.crawl.selenium.WebDriverCrawler;

/**
 * Created by anthonymottot on 28/10/2015.
 */
public class AlbertDetails extends WebDriverCrawler {

    private Logger logger = LogManager.getLogger(AlbertMap.class);

    private static final String GOOGLE_MAP_LINK = "//a[contains(., 'Zobrazit velkou mapu')]";

    @FindBy ( xpath = GOOGLE_MAP_LINK )
    WebElement weGoogleMap;

    AlbertDetails(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    Coordinates getCoordinates() {
        Coordinates toReturn = null;
        if (isVisible(GOOGLE_MAP_LINK, 5)) {
            String[] url = URLDecoder.decode(weGoogleMap.getAttribute("href")).split("=");
            String[] coord = url[3].split(",");
            try {
                toReturn = new Coordinates(Double.parseDouble(coord[0].trim()), Double.parseDouble(coord[1].trim()));
            }
            catch(NumberFormatException nfe) {
                Double latitude = fixParseDouble(coord[0].trim());
                Double longitude = fixParseDouble(coord[1].trim());

                toReturn = new Coordinates(latitude, longitude);
            }
        }

        return toReturn;
    }

    private Double fixParseDouble(String toBeFixed) {
        Pattern pattern = Pattern.compile("^[0-9.]");
        Matcher matcher = pattern.matcher(toBeFixed);

        String toBeFired = "";
        if ( matcher.matches() ) {
            while (matcher.find()) {
                toBeFired = matcher.group(0);
            }
        }

        if ( toBeFixed.contains("18.185052"))
            return Double.parseDouble("18.185052");
        else if ( toBeFixed.contains("18.243725"))
            return Double.parseDouble("18.243725");

        return Double.parseDouble(toBeFixed.replace(toBeFired, "").trim());
    }
}
