package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static redmine.managers.Manager.driver;

public class ProjectsPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//h2[text()='Проекты']")
    private WebElement projectPageName;

    @Step("Отображается страница 'Проекты'")
    public String projectPageName() {
        return projectPageName.getText();
    }

    @Step("Присутствует соответсвующее название проекта")
    public String projectName(String projectName) {
        String fullProjectXpath = String.format("//a[text()='%s']", projectName);
        return driver().findElement(By.xpath(fullProjectXpath)).getText();
    }

    @Step("Присутствует соответствующий элемент описания проекта")
    public String projectNameDescription(String projectName) {
        String fullProjectXpath = String.format("//a[text()='%s']/following-sibling::div", projectName);
        return driver().findElement(By.xpath(fullProjectXpath)).getText();
    }

    @Step("Отсутствует соответствующее название проекта")
    public WebElement projectNameAbsent(String projectName) {
        String fullProjectXpath = String.format("//a[text()='%s']", projectName);
        return driver().findElement(By.xpath(fullProjectXpath));
    }

    @Step("Отсутствует  соответствующий элемент описания проекта")
    public WebElement projectNameDescriptionAbsent(String projectName) {
        String fullProjectXpath = String.format("//a[text()='%s']/following-sibling::div", projectName);
        return driver().findElement(By.xpath(fullProjectXpath));
    }

}
