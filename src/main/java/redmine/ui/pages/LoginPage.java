package redmine.ui.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class LoginPage {
    private WebDriver driver;
    private WebElement loginElement;
    private WebElement passwordElement;
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        Objects.requireNonNull(driver,"Драйвер должен быть проинициализирован");
        this.driver=driver;

        loginElement=driver.findElement(By.xpath("//input[@id='username']"));;
        passwordElement=driver.findElement(By.xpath("//input[@id='password']"));
        submitButton=driver.findElement(By.xpath("//input[@id='login-submit']"));
    }
    public void  login(String login,String password){
     loginElement.sendKeys(login);
     passwordElement.sendKeys(password);
     submitButton.click();
    }

}
