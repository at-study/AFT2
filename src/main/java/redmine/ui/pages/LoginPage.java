package redmine.ui.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class LoginPage {
    private WebDriver driver;
    private WebElement loginElement=driver.findElement(By.xpath("//input[@id='username']"));;
    private WebElement passwordElement=driver.findElement(By.xpath("//input[@id='password']"));
    private WebElement submitButton=driver.findElement(By.xpath("//input[@id='login-submit']"));

    public LoginPage(WebDriver driver){
        Objects.requireNonNull(driver,"Драйвер должен быть проинициализирован");
        this.driver=driver;
    }
    public void  login(String login,String password){
     loginElement.sendKeys(login);
     passwordElement.sendKeys(password);
     submitButton.click();
    }

}
