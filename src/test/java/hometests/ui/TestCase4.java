package hometests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase4 {
    private User user;
    private Project project;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(true).setStatus(1).generate();
        project = new Project().setIsPublic(false).generate();
        openPage("login");
    }

    @Test(testName = " Видимость проекта. Приватный проект. Администратор", description = "Видимость проекта. Приватный проект. Администратор")
    @Description("4. Видимость проекта. Приватный проект. Администратор")
    public void visibilityOfPrivateProjectForAdmin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        displayOfHomePage();
        getPage(HeaderPage.class).projects.click();
        displayOfProjectPage();
        displayOfCreatedProject();
    }

    @Step("1. Отображается домашняя страница")
    private void displayOfHomePage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).home));
    }

    @Step("2. Отображается страница \"Проекты\"")
    private void displayOfProjectPage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(ProjectsPage.class).projectPageName));
    }

    @Step("3. На странице отображается проект из предусловия")
    private void displayOfCreatedProject() {
        Asserts.assertEquals(getPage(ProjectsPage.class).projectName(project.getName()), project.getName());
        Asserts.assertEquals(getPage(ProjectsPage.class).projectNameDescription(project.getName()), project.getDescription());
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}

