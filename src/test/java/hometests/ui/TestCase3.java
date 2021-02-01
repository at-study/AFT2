package hometests.ui;

import io.qameta.allure.Description;
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

    @Test(testName = "Авторизация НЕподтверждённым пользователем",  description = "Авторизация НЕподтверждённым пользователем")
    @Description("3. Авторизация неподтвержденным пользователем")
    public void unAcceptedUserLogin() {
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
        Asserts.assertEquals(getPage(HeaderPage.class).adminHomePage(), "Home");
        Asserts.assertEquals(getPage(LoginPage.class).errorMessage(), "Your account was created and is now pending administrator approval.");
        Assert.assertFalse(BrowserUtils.isElementPresent(getPage(HeaderPage.class).myPage));
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).signIn));
        Assert.assertTrue(BrowserUtils.isElementPresent(getPage(HeaderPage.class).register));
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
