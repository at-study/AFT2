package hometests.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.Property;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;

public class MyFirstTest {
    User user;
    private WebDriver driver;

    @BeforeMethod
    public void prepareFixture(){
        user=new User().generate();
        System.setProperty("webdriver.chrome.driver",Property.getStringProperty("webdriver.chrome.driver"));
        driver = new ChromeDriver();
        driver.get(Property.getStringProperty("ui.url")+"/login");
    }

    @Test
    public void myFirstLoginTest() {
        new LoginPage(driver).login("admin","admin123");
        Assert.assertEquals(new HeaderPage(driver).loggedAs(),"Вошли как admin");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}