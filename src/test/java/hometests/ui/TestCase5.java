package hometests.ui;

import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.project.Project;
import redmine.model.role.Role;
import redmine.model.role.RolePermissions;
import redmine.model.user.User;
import redmine.ui.pages.LoginPage;
import static redmine.utils.Asserts.assertEquals;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.model.role.RolePermission.VIEW_ISSUES;
import static redmine.ui.pages.Pages.getPage;

public class TestCase5 {
    private User user;
    private Project project;
    private Project project2;
    private Project project3;
    private Role role;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(1).generate();
        role = new Role().setPermissions(new RolePermissions(VIEW_ISSUES)).generate();
        project = new Project().setIsPublic(true).generate();
        project2 = new Project().setIsPublic(false).generate();
        project3 = new Project().setIsPublic(false).generate();
        openPage("login");
    }

    @Test(testName = " Видимость проектов. Пользователь", priority = 6, description = " Видимость проектов. Пользователь")
    @Description("5. Видимость проектов. Пользователь")
    public void visibiltyOfProjectForUser() {
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(role.getName());
        System.out.println(role.getPermissions());
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());


    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
