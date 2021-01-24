package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//table[@class='list users']")
   public WebElement table;
    @FindBy(xpath = "//h2[text()='Пользователи']")
    public WebElement usersPageName;

    public String table() {
        return table.getText();
    }
    public String usersPageName() {
        return usersPageName.getText();
    }
}
