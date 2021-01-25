package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//table[@class='list users']")
    public WebElement table;
    @FindBy(xpath = "//h2[text()='Пользователи']")
    public WebElement usersPageName;
    @FindBy(xpath = "//a[@class='icon icon-add']")
    public WebElement newUserAdd;

    @Step("Присутствует элемент 'Таблица пользователей' ")
    public String table() {
        return table.getText();
    }

    @Step("Открыта страница пользователи")
    public String usersPageName() {
        return usersPageName.getText();
    }

    @Step("Присутствует элемент 'Новый пользователь' ")
    public String newUserAdd() {
        return newUserAdd.getText();
    }
}
