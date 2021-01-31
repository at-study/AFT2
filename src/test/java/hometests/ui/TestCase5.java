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
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.Asserts;
import static redmine.db.requests.ProjectRequests.addUserAndRoleToProject;
import static redmine.managers.Manager.*;
import static redmine.model.role.RolePermission.VIEW_ISSUES;
import static redmine.ui.pages.Pages.getPage;

public class TestCase5 {
    private User user;
    private Project publicProject;
    public Project privateNotConnectedProject;
    private Project privateConnectedProject;
    private Project createdConnectedProject;
    private Role role;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(1).generate();
        role = new Role().setPermissions(new RolePermissions(VIEW_ISSUES)).generate();
        publicProject = new Project().setIsPublic(true).generate();
        privateNotConnectedProject = new Project().setIsPublic(false).generate();
        privateConnectedProject = new Project().setIsPublic(false).generate();
        createdConnectedProject = addUserAndRoleToProject(privateConnectedProject, user, role);
        openPage("login");
    }

    @Test(testName = " Видимость проектов. Пользователь", priority = 6, description = " Видимость проектов. Пользователь")
    @Description("5. Видимость проектов. Пользователь")
    public void visibiltyOfProjectForUser() {
        authorizationAndSituatingOnHomePage();
        clickAndPassingOnProjectPage();
        projectReflection();
    }

    @Step("Пользовель авторизировался и находиться на домашней странице")
    private void authorizationAndSituatingOnHomePage() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Asserts.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
    }

    @Step("Пользовель кликнул на элемент Проекты и перешёл на страницу Проекты")
    private void clickAndPassingOnProjectPage() {
        getPage(HeaderPage.class).projects.click();
        Asserts.assertEquals(getPage(ProjectsPage.class).projectPageName(), "Проекты");
    }

    @Step("  ***  Отображение проектов  ***  ")
    private void projectReflection() {
        publicProjectVisibility();
        privateNotConnectedProjectVisibility();
        privateConnectedProjectVisibility();
    }

    @Step("Отображается публичный проект")
    private void publicProjectVisibility() {
        Asserts.assertEquals(getPage(ProjectsPage.class).projectName(publicProject.getName()), publicProject.getName());
        Asserts.assertEquals(getPage(ProjectsPage.class).projectNameDescription(publicProject.getName()), publicProject.getDescription());
    }

    @Step("НЕ Отображается приватный  проект ( непривязанный )")
    private void privateNotConnectedProjectVisibility() {
        Assert.assertFalse(getPage(ProjectsPage.class).projectInListSituating(privateNotConnectedProject.getName()));
        Assert.assertFalse(getPage(ProjectsPage.class).projectDescriptionInListSituating(privateNotConnectedProject.getDescription()));
    }

    @Step("Отображается приватный  проект ( привязанный )")
    private void privateConnectedProjectVisibility() {
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
