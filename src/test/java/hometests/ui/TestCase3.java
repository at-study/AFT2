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
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase3 {
    private User user;

    @BeforeMethod
    public void prepareFixture() {
        user = new User().setAdmin(false).setStatus(2).generate();
        openPage("login");
    }

    @Test(testName = "Авторизация НЕподтверждённым пользователем", description = "Авторизация НЕподтверждённым пользователем")
    @Description("3. Авторизация неподтвержденным пользователем")
    public void unAcceptedUserLogin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).home));
        flashNoticeAboutAccount();
        notDisplayedElement();
        displayedElements();
    }

    @Step(" 2. Отображается ошибка с текстом 'Ваша учётная запись создана и ожидает подтверждения администратора.'")
    private void flashNoticeAboutAccount() {
        Asserts.assertEquals(getPage(LoginPage.class).errorMessage(), "Your account was created and is now pending administrator approval.");
    }

    @Step("3. В заголовке страницы не отображаются элементы 'Моя страница'")
    private void notDisplayedElement() {
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).myPage));
    }

    @Step("4. В заголовке страницы отображаются элементы \"Войти\", \"Регистрация\" ")
    private void displayedElements() {
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).signIn));
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).register));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
