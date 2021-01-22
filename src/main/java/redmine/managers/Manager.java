package redmine.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import redmine.Property;
import redmine.db.DataBaseConnection;

public class Manager {

    public final static DataBaseConnection dbConnection = new DataBaseConnection();
    //TODO на треадлокал когда будет многопоточность
    private static WebDriver driver;

    public static WebDriver driver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", Property.getStringProperty("webdriver.chrome.driver"));
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void driverQuit() {
        driver.quit();
        driver = null;
    }

    public static void openPage(String uri){
        driver().get(Property.getStringProperty("ui.url")+uri);
    }

}
