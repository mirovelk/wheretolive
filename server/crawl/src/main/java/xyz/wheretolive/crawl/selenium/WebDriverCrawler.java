package xyz.wheretolive.crawl.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by anthonymottot on 25/10/2015.
 */
public class WebDriverCrawler {

    public WebDriver webDriver;

    public WebDriverCrawler() {}

    public WebDriverCrawler(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static final int DEFAULT_TIMEOUT = 30;

    private WebElement findBy(By locator) {
        return webDriver.findElement(locator);
    }

    private WebElement findBy(String xpath) {
        return webDriver.findElement(By.xpath(xpath));
    }

    protected WebElement getWebElement(By locator) {
        return findBy(locator);
    }

    protected WebElement getWebElement(WebElement webElement, String locator) {
        return webElement.findElement(By.xpath(locator));
    }

    protected WebElement getWebElement(String xpath) {
        waitForVisibility(xpath);
        return findBy(xpath);
    }

    protected List<WebElement> getWebElements(WebElement webElement, By locator) {
        return webElement.findElements(locator);
    }

    protected List<WebElement> getWebElements(WebElement webElement, String xpath) {
        return webElement.findElements(By.xpath(xpath));
    }

    protected String getText(String xpath) {
        return getWebElement(xpath).getText();
    }

    protected String getText(WebElement webElement) {
        return webElement.findElement(By.xpath(".")).getText();
    }

    protected String getText(WebElement webElement, String xpath) {
        return webElement.findElement(By.xpath(xpath)).getText();
    }

    protected String getClassText(WebElement webElement, String xpath) {
        return webElement.findElement(By.xpath(xpath)).getAttribute("class");
    }

    protected boolean getAttribute(WebElement webElement, String attributeName) {
        return ( webElement.getAttribute(attributeName) != null ? true : false );
    }

    private void waitForVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitForVisibility(By locator, int timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout_in_seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitForVisibility(String xpath) {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    private void waitForVisibility(String xpath, int timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout_in_seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    private void waitForInvisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private void waitForInvisibility(By locator, int timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout_in_seconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private void waitForInvisibility(String xpath) {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    private void waitForInvisibility(String xpath, int timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout_in_seconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    private void waitForVisibilities(String xpath, int timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout_in_seconds);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    private void waitForVisibilities(String xpath) {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    protected boolean areVisible(String xpath) {
        boolean toReturn = true;
        try {
            waitForVisibilities(xpath, 5);
        } catch ( TimeoutException te ) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isInvisible(String xpath) {
        boolean toReturn = true;
        try {
            waitForInvisibility(xpath);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isInvisible(String xpath, boolean throwException) {
        boolean toReturn = true;

        if (!throwException)
            return isInvisible(xpath);

        waitForInvisibility(xpath);
        return toReturn;
    }

    protected boolean isInvisible(String xpath, int timeout_in_seconds) {
        boolean toReturn = true;
        try {
            waitForInvisibility(xpath, timeout_in_seconds);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isInvisible(By locator) {
        boolean toReturn = true;
        try {
            waitForInvisibility(locator);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isInvisible(By locator, int timeout_in_seconds) {
        boolean toReturn = true;
        try {
            waitForInvisibility(locator, timeout_in_seconds);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isVisible(String xpath) {
        boolean toReturn = true;
        try {
            waitForVisibility(xpath);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    public boolean isVisible(String xpath, boolean throwException) {
        boolean toReturn = true;

        if (!throwException)
            return isVisible(xpath);

        waitForVisibility(xpath);
        return toReturn;
    }

    protected boolean isVisible(String xpath, boolean throwException, int timeout_in_seconds) {
        boolean toReturn = true;

        if (!throwException)
            return isVisible(xpath, timeout_in_seconds);

        waitForVisibility(xpath, timeout_in_seconds);
        return toReturn;
    }

    protected boolean isVisible(String xpath, int timout_in_seconds) {
        boolean toReturn = true;
        try {
            waitForVisibility(xpath, timout_in_seconds);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isVisible(By locator) {
        boolean toReturn = true;
        try {
            waitForVisibility(locator);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    protected boolean isVisible(By locator, int timeout_in_seconds) {
        boolean toReturn = true;
        try {
            waitForVisibility(locator, timeout_in_seconds);
        } catch (TimeoutException te) {
            toReturn = false;
        } finally {
            return toReturn;
        }
    }

    public void get(String url) {
        webDriver.get(url);
    }

    public void moveTo(By by) {
        new Actions(webDriver).moveToElement(webDriver.findElement(by)).perform();
    }

    public void moveTo(String xpath) {
        new Actions(webDriver).moveToElement(webDriver.findElement(By.xpath(xpath))).perform();
    }

//    public void selectFromDropDown(String xpath, String valueToSelect) {
//        By currentBy = By.xpath(xpath + "/following-sibling::button");
//        waitForVisibility(currentBy);
//        moveTo(currentBy);
//        webDriver.findElement(currentBy).click();
//        By dropDownLocator = By.xpath(DROP_DOWN_LIST + "[text()='" + valueToSelect + "']");
//        By dropDownLocator = By.xpath(DROP_DOWN_LIST + "[contains(.,'" + valueToSelect + "')]");
//        waitForVisibility(dropDownLocator, DEFAULT_TIMEOUT);
//        webDriver.findElement(dropDownLocator).click();
//    }

//    public void type(String xpath, String valueToType) {
//        By currentBy = By.xpath(xpath);
//        waitForVisibility(currentBy);
//        moveTo(currentBy);
//        webDriver.findElement(currentBy).sendKeys(valueToType);
//        webDriver.findElement(currentBy).sendKeys(Keys.ENTER);
//    }

//    public void type(String label, String value) {
//        String xpath = "//label[contains(., '" + label +"')]following-sibling::input";
//        By currentBy = By.xpath(xpath);
//        waitForInvisibility(currentBy);
//        webDriver.findElement(currentBy).sendKeys(value);
//    }

//    public void scriptTimeout(int timeout) {
//        webDriver.manage().timeouts().setScriptTimeout(300, TimeUnit.SECONDS);
//    }

//    public void waitForPageToLoad(int timemout) {
//        webDriver.manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);
//    }

    public void click(String xpath) {
        webDriver.findElement(By.xpath(xpath)).click();
    }

    private void click(By by) {
        webDriver.findElement(by).click();
    }

    public void checkAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            //exception handling
        }
    }

}
