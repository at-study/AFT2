package redmine.managers;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
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

    public static DataBaseConnection dbConnection = new DataBaseConnection();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    @SneakyThrows
    public static WebDriver driver() {
        if (driver.get() == null) {
            driver.set(getPropertyDriver());
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Property.getIntegerProperty("ui.implicitly.wait"), TimeUnit.SECONDS);
            wait.set(new WebDriverWait(driver.get(), Property.getIntegerProperty("ui.condition.wait")));
        }
        return driver.get();
    }

    @Step("Выход из драйвера")
    public static void driverQuit() {
        if (driver.get() != null) {
            driver.get().quit();
        }
        driver.set(null);
    }

    public static WebDriverWait waiter() {
        return wait.get();
    }

    @Attachment(value = "screenshot")
    public static byte[] takesScreenshot() {
        return ((TakesScreenshot) driver()).getScreenshotAs(OutputType.BYTES);
    }

    public static JavascriptExecutor js() {
        return (JavascriptExecutor) driver();
    }

    @Step("Открыть страницу {0}")
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
