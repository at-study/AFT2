package hometests.ui;

import static redmine.utils.Asserts.assertEquals;

import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.AdminPage;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.UsersPage;
import redmine.utils.BrowserUtils;

import static org.testng.Assert.*;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase7 {
    private User userAdmin;
    private User user1;
    private User user2;
    private User user3;


    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        user1 = new User().setAdmin(false).setStatus(1).generate();
        user2 = new User().setAdmin(false).setStatus(1).generate();
        user3 = new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Сортировка списка пользователей по имени  и фамилии", priority = 7, description = "Администрирование. Сортировка списка пользователей по имени  и фамилии")
    @Description("7. Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void usersSortingForAdminByUserNameAndLastName() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        assertEquals(getPage(AdminPage.class).adminPageName(), "Администрирование");
        assertEquals(getPage(AdminPage.class).users(), "Пользователи");
        getPage(AdminPage.class).users.click();
        assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        assertTrue(BrowserUtils.isElementPresent(getPage(UsersPage.class).table));


    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
