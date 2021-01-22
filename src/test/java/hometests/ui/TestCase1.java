package hometests.ui;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase1 {
    User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(true).setStatus(1).generate();
        openPage("login");
    }
    @Test(testName = "Авторизация администратором", priority = 2, description = "Авторизация администратором")
    public void authorizationByAdminAndElements() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }

}
