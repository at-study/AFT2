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
    public WebElement myPage;
    @FindBy(xpath = "//a[@class='administration']")
    public WebElement administration;
    @FindBy(xpath = "//a[@class='help']")
    private WebElement help;
    @FindBy(xpath = "//a[@class='my-account']")
    private WebElement myAccount;
    @FindBy(xpath = "//a[@class='logout']")
    private WebElement logout;
    @FindBy(xpath = "//form/label/a")
    private WebElement searchLabel;
    @FindBy(xpath = "//form/input[@id='q']")
    public WebElement searchField;
    @FindBy(xpath = "//a[@class='login']")
    public WebElement signIn;
    @FindBy(xpath = "//a[@class='register']")
    public WebElement register;




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
    public String searсhLabel() {
        return searchLabel.getText();
    }

}
