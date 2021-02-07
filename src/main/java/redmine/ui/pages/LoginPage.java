package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.managers.Manager;
import redmine.ui.pages.helpers.CucumberName;

@CucumberName("Вход в систему")
public class LoginPage extends AbstractPage {


    @FindBy(xpath = "//input[@id='username']")
    public WebElement loginElement;

    @FindBy(xpath = "//input[@id='password']")
    public WebElement passwordElement;

    @FindBy(xpath = "//input[@id='login-submit']")
    public WebElement submitButton;

    @CucumberName("\"Your account was created and is now pending administrator approval.\"")
    @FindBy(xpath = "//div[@id='flash_error']")
    public WebElement flashError;

    @Step("Авторизация в Редмине c логином: {0} и паролем: {1}")
    public void login(String login, String password) {
        loginElement.sendKeys(login);
        passwordElement.sendKeys(password);
        Manager.takesScreenshot();
        submitButton.click();
    }


    @Step("Уведомление об ошибке")
    public String errorMessage() {
        return flashError.getText();
    }

}
