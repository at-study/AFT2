package bddTests.steps;

import cucumber.api.java.ru.И;
import redmine.managers.Context;
import redmine.model.project.Project;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.Asserts;

public class ProjectAssertSteps {
    @И("Отображается проект {string}")
    public void assertProjectNameAndDescriptionDisplayed(String projectStashId) {
        Project project = Context.get(projectStashId, Project.class);
        String projectExpectedName=project.getName();
        String projectExpectedDescription=project.getDescription();
        String actualName= ProjectsPage.projectName(projectExpectedName);
        String actualDescription=ProjectsPage.projectNameDescription(projectExpectedName);
        Asserts.assertEquals(actualName, projectExpectedName);
        Asserts.assertEquals(actualDescription, projectExpectedDescription);
    }
}
