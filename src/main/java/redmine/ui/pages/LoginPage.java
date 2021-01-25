package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement loginElement;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordElement;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='flash_error']")
    private WebElement flashError;

    @Step("Авторизация в Редмине")
    public void login(String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        submitButton.click();
    }
    @Step("Уведомление об ошибке")
    public String errorMessage() {
        return flashError.getText();
    }

}
