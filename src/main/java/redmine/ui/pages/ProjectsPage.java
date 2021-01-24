package redmine.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static redmine.managers.Manager.driver;

public class ProjectsPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//h2[text()='Проекты']")
    private WebElement projectPageName;

    public String projectPageName() {
        return projectPageName.getText();
    }

    public WebElement projectName(String projectName) {
        String fullProjectXpath=String.format("//a[text()='%s']",projectName);
        return driver().findElement(By.xpath(fullProjectXpath));
    }
}