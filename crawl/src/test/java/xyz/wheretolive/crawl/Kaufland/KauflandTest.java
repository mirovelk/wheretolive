package xyz.wheretolive.crawl.Kaufland;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wheretolive.core.SpringConfig;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.crawl.pageObject.KauflandMap;

import java.util.LinkedList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class KauflandTest implements IKaufland {

    private Logger logger = LogManager.getLogger(KauflandTest.class);

    WebDriver webDriver;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.get(KAUFLAND_MOBILE);
    }

    @After
    public void teardown() {
        webDriver.quit();
    }

    @Test
    public void getKauflandList() {
        KauflandMap kauflandMap = new KauflandMap(webDriver);

        kauflandMap
                .search("česká republika")
                .loadAllResults();

        List<WebElement> kauflandItems = kauflandMap.getResults();
        List<MapObject> geoKaufland = new LinkedList<>();

        for ( int i = 0; i < kauflandItems.size(); i++) {
//            logger.debug(kauflandItems.get(i).findElement(By.xpath("./span[@class='msf-storelist-item-text']/span")).getText().replaceAll("<br>", "").trim());
            logger.debug(kauflandMap.getGoogleUrlDetails(kauflandItems.get(i)));
            geoKaufland.add(new MapObject(kauflandMap.getGoogleUrlDetails(kauflandItems.get(i))));
        }
    }

}
