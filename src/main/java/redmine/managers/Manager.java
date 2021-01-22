package redmine.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import redmine.Property;
import redmine.db.DataBaseConnection;

public class Manager {

    public final static DataBaseConnection dbConnection = new DataBaseConnection();
    //TODO на треадлокал когда будет многопоточность
    private static WebDriver driver;

    public static WebDriver driver() {
        if (driver == null) {
            driver=getPropertyDriver();
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

    private static WebDriver getPropertyDriver(){
       switch( Property.getStringProperty("browser")){
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
