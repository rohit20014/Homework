package Util;

import Page.StoreAPI;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.List;

public class Helper {
    final static Logger logger = Logger.getLogger(Helper.class);
    ReadPropFile prop = new ReadPropFile();
    StoreAPI api = new StoreAPI();
    public static WebDriver driver;
    public static WebDriverWait waitLog;

    @BeforeSuite(alwaysRun = true)
    public void setupBrowser() {
        String browser = prop.getPropertyValue("browser");
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            if (prop.getPropertyValue("headless").equalsIgnoreCase("yes")) {
                chromeOptions.addArguments("headless", "window-size=1920x1080");
            }
            driver = new ChromeDriver(chromeOptions);
        } else {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        logger.info("Browser is Initiated");
        waitLog = new WebDriverWait(driver, 30);
        driver.manage().window().maximize();
        logger.info("Browser window is maximized");
        String userId = api.postUser();
        System.out.println("New User ID: " + userId);
        prop.setPropertyValue("userId",userId);
        api.getThreeBookName();
    }

    public WebElement findElementByXpath(String elementPath){
        final WebElement ele = waitLog.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementPath)));
        return ele;
    }

    public WebElement findElementById(String elementPath){
        final WebElement ele = waitLog.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementPath)));
        return ele;
    }

    public void moveFocusToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        logger.info("element moved to: " + element.getText());
    }

    public Boolean checkVisibilityOfElement(String elementPath){
        final WebElement ele = waitLog.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementPath)));
        Boolean bool = ele.isDisplayed();
        System.out.println(bool);
        Assert.assertEquals(bool, Boolean.TRUE);
        return bool;
    }

//    public String getPropertyValue(String key) {
//        String value = prop.getPropertyValue(key);
//        return value;
//    }

    public void verifyTextViaAPI(WebElement element, String title, String attributeName) {
        String value = api.getBookDetails(title, attributeName);
        verifyText(element, value);
    }

    public Select selectDropdown(String elementPath) {
        Select ele = new Select(waitLog.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementPath))));
        return ele;
    }

    public List<WebElement> elementsList(String element) {
        List<WebElement> list = waitLog.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(element)));
        return list;
    }

    public void verifyText(WebElement element, String expectedText) {
        String actualText = element.getText();
        System.out.println("Actual: " + actualText);
        Assert.assertEquals(actualText, expectedText);
        logger.info("Assertion on Actual text : " + actualText + " applied");
    }

    public void verifyText(String actualText, String expectedText) {
        Assert.assertEquals(actualText, expectedText);
        logger.info("Assertion on Actual text : " + actualText + " applied");
    }

    public void waitForAlertAndAccept(String bookType) {
        //switch case would be better option
        waitLog.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        if(bookType.equalsIgnoreCase("new")) {
            verifyText(alertText, prop.getPropertyValue("newBookAlertText"));
        } else if(bookType.equalsIgnoreCase("Added")){
            verifyText(alertText, prop.getPropertyValue("bookPresentAlertText"));
        } else if(bookType.equalsIgnoreCase("one")) {
            verifyText(alertText, prop.getPropertyValue("oneBookDeleteAlertText"));
        } else if(bookType.equalsIgnoreCase("all")) {
            verifyText(alertText, prop.getPropertyValue("allBookDeleteAlertText"));
        } else if(bookType.equalsIgnoreCase("No Book")) {
            verifyText(alertText, prop.getPropertyValue("noBooksAvailableAlertText"));
        } else if(bookType.equalsIgnoreCase("account")) {
            verifyText(alertText, prop.getPropertyValue("userDeleteAlertText"));
        } else {
            logger.info("No option selected for bookType : " + bookType);
        }
        driver.switchTo().alert().accept();
    }

    public void switchFrame(String Locator) {
        WebElement innerFrame = findElementByXpath(Locator);
        driver.switchTo().frame(innerFrame);
        logger.info("window is switched to specified iFrame i.e. : " + innerFrame);
    }

    public void switchToMainWindow() {
        driver.switchTo().window(driver.getWindowHandle());
        logger.info("window is switched to default window");
    }

    public String currentUrl() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }

    public void forceWait(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void close() {
        api.deleteUsersAllBooks();
        api.deleteUser();
        driver.quit();
    }
}
