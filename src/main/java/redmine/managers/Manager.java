package redmine.managers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import redmine.Property;
import redmine.db.DataBaseConnection;

import java.util.concurrent.TimeUnit;

public class Manager {

    public final static DataBaseConnection dbConnection = new DataBaseConnection();
    //TODO на треадлокал когда будет многопоточность
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriver driver() {
        if (driver == null) {
            driver = getPropertyDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
            wait=new WebDriverWait(driver,Property.getIntegerProperty("ui.condition.wait")) ;
        }
        return driver;
    }

    public static void driverQuit() {
        driver.quit();
        driver = null;
    }

    public static WebDriverWait waiter(){
        return wait;
    }

    public static byte[] takesScreenshot(){
        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }

    public static JavascriptExecutor js(){
        return (JavascriptExecutor) driver();
    }

    public static void openPage(String uri) {
        driver().get(Property.getStringProperty("ui.url") + uri);
    }

    private static WebDriver getPropertyDriver() {
        switch (Property.getStringProperty("browser")) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", Property.getStringProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
                return new ChromeDriver();
            case "firefox":
                System.setProperty("webdriver.gecko.driver", Property.getStringProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("Неизвестный тип браузера (добавить в Менеджер и Properties !)");
        }
    }
}
