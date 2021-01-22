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
    private WebElement loggeAs;

    public String loggedAs() {
        return loggeAs.getText();
    }
}
