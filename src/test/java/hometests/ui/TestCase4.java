package hometests.ui;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.ProjectsPage;
import redmine.utils.BrowserUtils;

import static redmine.managers.Manager.*;
import static redmine.ui.pages.Pages.getPage;

public class TestCase4 {
    User user;
    Project project;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(true).setStatus(1).generate();
        project = new Project().setIsPublic(false).generate();
        openPage("login");
    }

    @Test(testName = " Видимость проекта. Приватный проект. Администратор", priority = 5, description = " Видимость проекта. Приватный проект. Администратор")
    @Description("4. Видимость проекта. Приватный проект. Администратор")
    public void visibilityOfPrivateProjectForAdmin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        getPage(HeaderPage.class).projects.click();
        Assert.assertEquals(getPage(ProjectsPage.class).projectPageName(), "Проекты");
        Assert.assertEquals(getPage(ProjectsPage.class).projectName(project.getName()), project.getName());
        Assert.assertEquals(getPage(ProjectsPage.class).projectNameDescription(project.getName()), project.getDescription());
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}

