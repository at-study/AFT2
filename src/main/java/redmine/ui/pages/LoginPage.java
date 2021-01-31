package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.managers.Manager;

public class LoginPage extends AbstractPage {

    @FindBy(xpath = "//input[@id='username']")
    private WebElement loginElement;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordElement;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='flash_error']")
    private WebElement flashError;

    @Step("Авторизация в Редмине c логином: {0} и паролём: {1}")
    public void login(String login, String password) {
        loginElement.sendKeys(login);
        Manager.takesScreenshot();
        passwordElement.sendKeys(password);
        Manager.takesScreenshot();
        submitButton.click();
        Manager.takesScreenshot();
    }

    @Step("Уведомление об ошибке")
    public String errorMessage() {
        return flashError.getText();
    }

}
