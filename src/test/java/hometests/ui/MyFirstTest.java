package hometests.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class MyFirstTest {
    @Test
    public void myFirstLoginTest() {
        WebDriver driver = new ChromeDriver();
    }
}