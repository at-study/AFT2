package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;
import static redmine.managers.Manager.driver;

public class ProjectsPage extends AbstractPage {
    private WebDriver driver;

    @FindBy(xpath = "//li[@class='root']")
    public List<WebElement> projectList;

    @FindBy(xpath = "//h2[text()='Проекты']")
    private WebElement projectPageName;

    @Step("Отображается страница 'Проекты'")
    public String projectPageName() {
        return projectPageName.getText();
    }

    @Step("Присутствует соответсвующее название проекта")
    public String projectName(String projectName) {
        String fullProjectNameXpath = String.format("//a[text()='%s']", projectName);
        return driver().findElement(By.xpath(fullProjectNameXpath)).getText();
    }

    @Step("Присутствует соответствующий элемент описания проекта")
    public String projectNameDescription(String projectName) {
        String fullProjectDescriptionXpath = String.format("//a[text()='%s']/following-sibling::div", projectName);
        return driver().findElement(By.xpath(fullProjectDescriptionXpath)).getText();
    }

    @Step("Проверка ОТСУТСТВИЯ названия поекта в списке проектов")
    public boolean projectInListSituating(String projectName) {
        return projectList.stream().map(element -> element.getText())
                .anyMatch(text -> text.equals(projectName));
    }

    @Step("Проверка ОТСУТСТВИЯ описания поекта в списке проектов")
    public boolean projectDescriptionInListSituating(String projectDescription) {
        return projectList.stream().map(element -> element.getText())
                .anyMatch(text -> text.equals(projectDescription));
    }
}
