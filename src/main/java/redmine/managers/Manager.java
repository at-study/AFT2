package redmine.managers;

import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import redmine.Property;
import redmine.db.DataBaseConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Manager {

    public final static DataBaseConnection dbConnection = new DataBaseConnection();
    //TODO на треадлокал когда будет многопоточность
    private static WebDriver driver;
    private static WebDriverWait wait;

    @SneakyThrows
    public static WebDriver driver() {
        if (driver == null) {
            driver = getPropertyDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, Property.getIntegerProperty("ui.condition.wait"));
        }
        return driver;
    }

    public static void driverQuit() {
        driver.quit();
        driver = null;
    }

    public static WebDriverWait waiter() {
        return wait;
    }

    public static byte[] takesScreenshot() {
        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }

    public static JavascriptExecutor js() {
        return (JavascriptExecutor) driver();
    }

    public static void openPage(String uri) {
        driver().get(Property.getStringProperty("ui.url") + uri);
    }

    private static WebDriver getPropertyDriver() throws MalformedURLException {
        if (Property.getBooleanProperty("remote")) {
            MutableCapabilities capabilities = new ChromeOptions();
            capabilities.setCapability("browserName", Property.getStringProperty("browser"));
            capabilities.setCapability("browserVersion", Property.getStringProperty("browser.version"));
            Map<String, Object> selenoidOptions = ImmutableMap.of("enableVNC", Property.getBooleanProperty("enable.vnc"), "enableVideo", Property.getBooleanProperty("enable.video"));
            capabilities.setCapability("selenoid:options", selenoidOptions);
            return new RemoteWebDriver(new URL(Property.getStringProperty("selenoid.hub.url")), capabilities);
        } else {
            switch (Property.getStringProperty("browser")) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", Property.getStringProperty("webdriver.chrome.driver"));
                    return new ChromeDriver();

                case "firefox":
                    System.setProperty("webdriver.gecko.driver", Property.getStringProperty("webdriver.gecko.driver"));
                    return new FirefoxDriver();

                default:
                    throw new IllegalArgumentException("Неизвестный тип браузера (добавить в Менеджер и Properties !)");
            }
        }

    }
}
