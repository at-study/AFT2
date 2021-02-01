package hometests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.utils.BrowserUtils;

import static redmine.utils.Asserts.assertEquals;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase2 {
    private User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = "Авторизация подтверждённым пользователем", description = "Авторизация подтверждённым пользователем")
    @Description("2. Авторизация подтвержденным пользователем")
    public void acceptedUserLogin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        displayOfElements();
        notDisplayOfElements();
        displayOfSearch();
    }

    @Step("Oтображаются элементы: \"Домашняя страница\", \"Моя страница\", \"Проекты\", \"Помощь\", \"Моя учётная запись\", \"Выйти\"")
    private void displayOfElements() {
        assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).home));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).myPage));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).projects));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).help));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).myAccount));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).logout));
    }

    @Step("В заголовке страницы не отображаются элементы 'Администрирование', 'Войти','Регистрация'")
    private void notDisplayOfElements() {
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).administration));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).signIn));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).register));
    }

    @Step("Отображается элемент \"Поиск\"")
    private void displayOfSearch() {
        assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).searchField));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}


