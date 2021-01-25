package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersNewPage extends AbstractPage{
    private WebDriver driver;

    @FindBy(xpath = "//h2[text()=' » Новый пользователь']")
    private WebElement newUserPage;
    @FindBy(xpath = "//input[@id='user_login']")
    public WebElement usernameField;
    @FindBy(xpath = "//input[@id='user_firstname']")
    public WebElement userFirstNameField;
    @FindBy(xpath = "//input[@id='user_lastname']")
    public WebElement userLastNameField;
    @FindBy(xpath = "//input[@id='user_mail']")
    public WebElement userMailField;
    @FindBy(xpath = "//input[@id='user_generate_password']")
    public WebElement passwordCreationCheckBox;
    @FindBy(xpath = "//input[@name='commit']")
    public WebElement commit;

    public String newUserPage() {
        return newUserPage.getText();
    }
}
