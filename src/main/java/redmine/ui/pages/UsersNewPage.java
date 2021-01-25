package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersNewPage extends AbstractPage{
    private WebDriver driver;

    @FindBy(xpath = "//a[text()='Пользователи']")
    private WebElement users;


    public String users() {
        return users.getText();
    }
}
