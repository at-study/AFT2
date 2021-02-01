package hometests.ui;

import io.qameta.allure.Description;
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
import static redmine.utils.Asserts.assertEquals;

public class TestCase1 {
    private User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(true).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = "Авторизация администратором")
    @Description("1. Авторизация администратором")
    public void administratorLogin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).adminHomePage));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).myPage));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).projects));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).administration));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).help));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).myAccount));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).logout));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).signIn));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).register));
        assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).searchField));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
