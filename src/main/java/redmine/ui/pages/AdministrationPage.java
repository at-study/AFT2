package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdministrationPage extends AbstractPage {

    @FindBy(xpath = "//h2[text()='Администрирование']")
    public WebElement adminPageName;

    @FindBy(xpath = "//a[@class='icon icon-user users']")
    public WebElement users;

    @Step("Элемент 'Администрирование' отображается")
    public String adminPageName() {
        return adminPageName.getText();
    }

    @Step("Элемент 'Пользователи' отображается")
    public String users() {
        return users.getText();
    }
}
