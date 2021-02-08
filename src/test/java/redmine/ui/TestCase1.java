package redmine.ui;

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

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.helpers.Pages.getPage;
import static redmine.utils.Asserts.assertEquals;

public class TestCase1 {
    private User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(true).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = "Авторизация администратором", description = "Авторизация администратором")
    @Description("1. Авторизация администратором")
    public void administratorLogin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        displayOfHomePage();
        displayOfLoggedAsElement();
        displayOfElements();
        notDisplayOfElements();
        displayOfSearch();
    }

    @Step("1. Отображается домашняя страница")
    private void displayOfHomePage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).home));
    }

    @Step("2. Отображается 'Вошли как <логин пользователя>'")
    private void displayOfLoggedAsElement() {
        assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
    }

    @Step("3. В заголовке страницы отображаются элементы: \"Домашняя страница\", \"Моя страница\", \"Проекты\", \"Администрирование\", \"Помощь\", \"Моя учётная запись\", \"Выйти\"'")
    private void displayOfElements() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).adminHomePage));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).myPage));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).projects));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).administration));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).help));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).myAccount));
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).logout));
    }

    @Step("4.В заголовке страницы не отображаются элементы 'Войти','Регистрация'")
    private void notDisplayOfElements() {
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).signIn));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).register));
    }

    @Step("5.Отображается элемент  \"Поиск\"")
    private void displayOfSearch() {
        assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).searchField));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
