package hometests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.role.Role;
import redmine.model.role.RolePermissions;
import redmine.model.user.Language;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.model.project.Project.addUserAndRoleToProject;
import static redmine.model.role.RolePermission.VIEW_ISSUES;
import static redmine.ui.pages.helpers.Pages.getPage;

public class TestCase5 {
    private User user;
    private Project publicProject;
    private Project privateNotConnectedProject;
    Role role;
    private Project createdConnectedProject;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(1).setLanguage(Language.RU).generate();
        role = new Role().setPermissions(new RolePermissions(VIEW_ISSUES)).generate();
        publicProject = new Project().setIsPublic(true).generate();
        privateNotConnectedProject = new Project().setIsPublic(false).generate();
        Project privateConnectedProject = new Project().setIsPublic(false).generate();
        createdConnectedProject = addUserAndRoleToProject(privateConnectedProject, user, role);
        openPage("login");
    }

    @Test(testName = " Видимость проектов. Пользователь", description = "Видимость проектов. Пользователь")
    @Description("5. Видимость проектов. Пользователь")
    public void visibilityOfProjectsForUser() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        displayOfHomePage();
        displayOfProjectPage();
        clickAndPassingOnProjectPage();
        assertPublicProjectIsDisplayed();
        assertPrivateProjectWithoutRoleIsNotDisplayed();
        assertPrivateProjectWithRoleIsDisplayed();
    }

    @Step("1. Отображается домашняя страница")
    private void displayOfHomePage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).home));
    }

    @Step("2.Отображается страница \"Проекты\"")
    private void displayOfProjectPage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).projects));
    }

    @Step("Пользователь кликнул на элемент Проекты и перешёл на страницу Проекты")
    private void clickAndPassingOnProjectPage() {
        getPage(HeaderPage.class).projects.click();
        Asserts.assertEquals(getPage(ProjectsPage.class).projectPageName(), "Проекты");
    }

    @Step("4.Отображается публичный проект")
    private void assertPublicProjectIsDisplayed() {
        Asserts.assertEquals(getPage(ProjectsPage.class).projectName(publicProject.getName()), publicProject.getName());
        Asserts.assertEquals(getPage(ProjectsPage.class).projectNameDescription(publicProject.getName()), publicProject.getDescription());
    }

    @Step("5.НЕ Отображается приватный  проект ( непривязанный )")
    private void assertPrivateProjectWithoutRoleIsNotDisplayed() {
        Assert.assertFalse(getPage(ProjectsPage.class).isProjectNameIsSituatingInListOfProjects(privateNotConnectedProject.getName()));
        Assert.assertFalse(getPage(ProjectsPage.class).isProjectDescriptionIsSituatingInListOfProjects(privateNotConnectedProject.getDescription()));
    }

    @Step("6.Отображается приватный  проект ( привязанный )")
    private void assertPrivateProjectWithRoleIsDisplayed() {
        String createdProjectName = createdConnectedProject.getName();
        String createdProjectDescription = createdConnectedProject.getDescription();
        Asserts.assertEquals(getPage(ProjectsPage.class).projectName(createdProjectName), createdProjectName);
        Asserts.assertEquals(getPage(ProjectsPage.class).projectNameDescription(createdProjectName), createdProjectDescription);
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
