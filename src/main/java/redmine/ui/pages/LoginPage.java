package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static redmine.managers.Manager.driver;

public class LoginPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement loginElement;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordElement;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement submitButton;

    public void login(String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        submitButton.click();
    }

}
