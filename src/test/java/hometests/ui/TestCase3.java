package hometests.ui;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.utils.BrowserUtils;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase3 {
    User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(2).generate();
        openPage("login");
    }

    @Test(testName = "Авторизация НЕподтверждённым пользователем", priority = 4, description = "Авторизация НЕподтверждённым пользователем")
    public void authorizationByUnacceptedUserAndElements() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).adminHomePage(), "Home");
        Assert.assertEquals(getPage(LoginPage.class).errorMessage(), "Your account was created and is now pending administrator approval.");
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).myPage));
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).signIn));
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).register));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
