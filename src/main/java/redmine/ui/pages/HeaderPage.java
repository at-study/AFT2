package redmine.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static redmine.managers.Manager.*;

public class HeaderPage {
    private WebDriver driver;
    @FindBy(xpath = "//a[@class='home']")
    private WebElement home;
    @FindBy(xpath = "//a[@class='projects']")
    private WebElement projects;
    @FindBy(xpath = "//div[@id='loggedas']")
    private WebElement loggeAs;

    public HeaderPage() {
        PageFactory.initElements(driver(), this);
    }

    public String loggedAs() {
        return loggeAs.getText();
    }
}
