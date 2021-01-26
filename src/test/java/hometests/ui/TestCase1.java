package hometests.ui;

import io.qameta.allure.Description;
import org.testng.Assert;
import static redmine.utils.Asserts.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

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
    @Description("1. Авторизация администратором")
    public void authorizationByAdminAndElements() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Asserts.assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
        Asserts.assertEquals(getPage(HeaderPage.class).adminHomePage(), "Домашняя страница");
        Asserts.assertEquals(getPage(HeaderPage.class).myPage(), "Моя страница");
        Asserts.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        Asserts.assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        Asserts.assertEquals(getPage(HeaderPage.class).help(), "Помощь");
        Asserts.assertEquals(getPage(HeaderPage.class).myAccount(), "Моя учётная запись");
        Asserts.assertEquals(getPage(HeaderPage.class).logout(), "Выйти");
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).signIn));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).register));
        Asserts.assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).searchField));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
