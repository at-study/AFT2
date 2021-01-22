package hometests.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyFirstTest {
    private WebDriver driver;

    @BeforeMethod
    public void prepareFixture(){
        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void myFirstLoginTest() {

    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}