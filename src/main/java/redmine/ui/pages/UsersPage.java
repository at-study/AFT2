package redmine.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.pages.helpers.CucumberName;

import java.util.List;

@CucumberName("Страница пользователи")
public class UsersPage extends AbstractPage {

    @FindBy(xpath = "//table[@class='list users']")
    public WebElement table;

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

}
