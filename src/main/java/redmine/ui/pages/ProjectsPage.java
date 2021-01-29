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

    @Step("Отсуствует соответсвующее название проекта")
    public String projectNameAbsent(String projectName) {
        String fullProjectXpath = String.format("//a[text()='%s']", projectName);
        if(driver().findElement(By.xpath(fullProjectXpath)).getText()!=null)
            return driver().findElement(By.xpath(fullProjectXpath)).getText() ;
        else return "Отсутствует Название проекта";
    }

    @Step("Отсутствует соответствующий элемент описания проекта")
    public String projectNameDescriptionAbsent(String projectName) {
        String fullProjectXpath = String.format("//a[text()='%s']/following-sibling::div", projectName);
        if(driver().findElement(By.xpath(fullProjectXpath)).getText()!=null)
            return driver().findElement(By.xpath(fullProjectXpath)).getText() ;
        else return "Отсутствует описание проекта";
    }
}
