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

    @Test(testName = "Авторизация подтверждённым пользователем", priority = 3, description = "Авторизация подтверждённым пользователем")

    @Description("2. Авторизация подтвержденным пользователем")
    public void authorizationByAcceptedUserAndElements() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        displayedElements();
        notDisplayedElements();
        searchIsDisplayed();
    }

    @Step("Oтображаются элементы: \"Домашняя страница\", \"Моя страница\", \"Проекты\", \"Помощь\", \"Моя учётная запись\", \"Выйти\"")
    private void displayedElements() {
        assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
        assertEquals(getPage(HeaderPage.class).adminHomePage(), "Домашняя страница");
        assertEquals(getPage(HeaderPage.class).myPage(), "Моя страница");
        assertEquals(getPage(HeaderPage.class).projects(), "Проекты");
        assertEquals(getPage(HeaderPage.class).help(), "Помощь");
        assertEquals(getPage(HeaderPage.class).myAccount(), "Моя учётная запись");
        assertEquals(getPage(HeaderPage.class).logout(), "Выйти");
    }

    @Step("В заголовке страницы не отображаются элементы 'Администрирование', 'Войти','Регистрация'")
    private void notDisplayedElements() {
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).administration));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).signIn));
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).register));
    }

    @Step("Отображается элемент \"Поиск\"")
    private void searchIsDisplayed() {
        assertEquals(getPage(HeaderPage.class).searchLabel(), "Поиск");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).searchField));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}


