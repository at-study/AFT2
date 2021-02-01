package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UsersPage extends AbstractPage {

    @FindBy(xpath = "//table[@class='list users']")
    public WebElement table;

    @FindBy(xpath = "//h2[text()='Пользователи']")
    public WebElement usersPageName;

    @FindBy(xpath = "//a[@class='icon icon-add']")
    public WebElement addNewUser;

    @FindBy(xpath = "//table[@class='list users']//a[text()='Пользователь']")
    public WebElement usersHeaderInTable;

    @FindBy(xpath = "//tr[@class='user active']//td[@class='username']")
    public List<WebElement> listOfUsersInTableByUsername;

    @FindBy(xpath = "//tr[@class='user active']//td[@class='lastname']")
    public List<WebElement> listOfUsersInTableByLastNames;

    @FindBy(xpath = "//tr[@class='user active']//td[@class='firstname']")
    public List<WebElement> listOfUsersInTableByNames;

    @FindBy(xpath = "//table[@class='list users']//a[text()='Фамилия']")
    public WebElement usersByLastNameHeaderInTable;

    @FindBy(xpath = "//table[@class='list users']//a[text()='Имя']")
    public WebElement usersByNameHeaderInTable;

    public String table() {
        return table.getText();
    }

    @Step("Присутствует элемент 'Пользователи' ")
    public String usersPageName() {
        return usersPageName.getText();
    }

    @Step("Присутствует элемент  'Новый пользователь'")
    public String newUserAdd() {
        return addNewUser.getText();
    }
}
