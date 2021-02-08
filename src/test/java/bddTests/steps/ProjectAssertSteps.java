package bddTests.steps;

import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.project.Project;
import redmine.ui.pages.ProjectsPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;

import java.util.Map;

public class ProjectAssertSteps {
    @И("Отображается проект {string}")
    public void assertProjectNameAndDescriptionDisplayed(String projectStashId, Map<String, String> parameters) {
        Project project = Context.get(projectStashId, Project.class);
        ParametersValidator.validateProjectParameters(parameters);
        String projectExpectedName=project.getName();
        String projectExpectedDescription=project.getDescription();
        String nameForActualProject=ProjectsPage.projectName(projectName);
        String descriptionForActualProject=ProjectsPage.projectNameDescription(projectName);

        WebElement elementName = CucumberPageObjectHelper.getElementBy("Страница Проекты", nameForActualProject);
        WebElement elementDescription = CucumberPageObjectHelper.getElementBy("Страница Проекты", descriptionForActualProject);

        Asserts.assertEquals(elementName.getText(), projectExpectedName);
        Asserts.assertEquals(elementDescription.getText(), projectExpectedDescription);
    }
}
