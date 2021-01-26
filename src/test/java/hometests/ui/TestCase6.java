package hometests.ui;
import static redmine.utils.Asserts.*;
import static redmine.utils.Asserts.assertEquals;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.*;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase6 {
    User userAdmin;
    User user1;
    User user2;
    User user3;


    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        user1 = new User().setAdmin(false).setStatus(1).generate();
        user2 = new User().setAdmin(false).setStatus(1).generate();
        user3 = new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Сортировка списка пользователей по пользователю", priority = 7, description = "Администрирование. Сортировка списка пользователей по пользователю")
    @Description("6. Администрирование. Сортировка списка пользователей по пользователю")
    public void usersSortingForAdminByUser() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        assertEquals(getPage(AdminPage.class).adminPageName(), "Администрирование");
        assertEquals(getPage(AdminPage.class).users(), "Пользователи");
        getPage(AdminPage.class).users.click();
        assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(UsersPage.class).table));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}

