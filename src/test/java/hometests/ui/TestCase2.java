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

public class TestCase2 {

    User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = "Авторизация подтверждённым пользователем", priority = 3, description = "Авторизация подтверждённым пользователем")
    public void authorizationByAdminAndElements() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
        Assert.assertEquals(getPage(HeaderPage.class).adminHomePage(), "Домашняя страница");
        Assert.assertEquals(getPage(HeaderPage.class).myPage(), "Моя страница");
        Assert.assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        Assert.assertEquals(getPage(HeaderPage.class).help(), "Помощь");
        Assert.assertEquals(getPage(HeaderPage.class).myAccount(), "Моя учётная запись");
        Assert.assertEquals(getPage(HeaderPage.class).logout(), "Выйти");
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).administration));
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).signIn));
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).register));
        Assert.assertEquals(getPage(HeaderPage.class).searсhLabel(), "Поиск");
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).searchField));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}


