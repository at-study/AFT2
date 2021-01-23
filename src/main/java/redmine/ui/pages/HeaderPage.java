package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HeaderPage extends AbstractPage {
    private WebDriver driver;
    @FindBy(xpath = "//a[@class='home']")
    private WebElement home;
    @FindBy(xpath = "//a[@class='projects']")
    private WebElement projects;
    @FindBy(xpath = "//div[@id='loggedas']")
    private WebElement loggedAs;
    @FindBy(xpath = "//a[@class='home']")
    private WebElement adminHomePage;
    @FindBy(xpath = "//a[@class='my-page']")
    private WebElement myPage;
    @FindBy(xpath = "//a[@class='administration']")
    private WebElement administration;
    @FindBy(xpath = "//a[@class='help']")
    private WebElement help;
    @FindBy(xpath = "//a[@class='my-account']")
    private WebElement myAccount;
    @FindBy(xpath = "//a[@class='logout']")
    private WebElement logout;
    @FindBy(xpath = "//a[@class='login']")
    private WebElement signIn;
    @FindBy(xpath = "//a[@class='register']")
    private WebElement register;
    @FindBy(xpath = "//div[@id='q']")
    private WebElement searchField;



    public String loggedAs() {
        return loggedAs.getText();
    }
    public String adminHomePage() {
        return adminHomePage.getText();
    }

    public String myPage() {
        return myPage.getText();
    }

    public String projects() {
        return projects.getText();
    }

    public String administration() {
        return administration.getText();
    }
    public String help() {
        return help.getText();
    }

    public String myAccount() {
        return myAccount.getText();
    }
    public String logout() {
        return logout.getText();
    }
    public boolean signIn() {
        if(signIn.isEnabled()){return true;}
        else return false;
    }
    public boolean register() {
        if(register.isDisplayed()){return true;}
        else return false;
    }
    public boolean searchField() {
    if(searchField.isDisplayed()){return true;}
    else return false;
    }
}
