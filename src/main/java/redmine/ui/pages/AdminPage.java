package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//h2[text()='Администрирование']")
    private WebElement adminPageName;
    @FindBy(xpath = "//a[@class='icon icon-user users']")
    public WebElement users;

    @Step("Открыта страница Администрирование")
    public String adminPageName() {
        return adminPageName.getText();
    }

    @Step("Присутсвует иконка/ссылка Пользователи")
    public String users() {
        return users.getText();
    }
}
