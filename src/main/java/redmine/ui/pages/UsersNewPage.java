package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersNewPage extends AbstractPage{
    private WebDriver driver;

    @FindBy(xpath = "//h2[text()='Новый пользователь']")
    private WebElement newUserPage;


    public String newUserPage() {
        return newUserPage.getText();
    }
}
