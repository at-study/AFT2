package hometests.ui;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.interfaces.Request;
import redmine.db.requests.UserRequests;
import redmine.model.user.User;
import redmine.ui.pages.*;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase8 {
    User userAdmin;
    User user;

    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Создание пользователя", priority = 7, description = "Администрирование. Создание пользователя")
    @Description("8.Администрирование. Создание пользователя.")
    public void usersCreationForAdmin() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        Assert.assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        Assert.assertEquals(getPage(AdminPage.class).adminPageName(), "Администрирование");
        Assert.assertEquals(getPage(AdminPage.class).users(), "Пользователи");
        getPage(AdminPage.class).users.click();
        Assert.assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        Assert.assertEquals(getPage(UsersPage.class).newUserAdd(), "Новый пользователь");
        getPage(UsersPage.class).newUserAdd.click();
        Assert.assertEquals(getPage(UsersNewPage.class).newUserPage(), "Пользователи » Новый пользователь");

        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();

        getPage(UsersNewPage.class).userCreation(login, firstName, lastName, mail);
        String flashNoticeText = String.format("Пользователь %s создан.", login);
        Assert.assertEquals(getPage(UsersNewPage.class).flashNotice(), flashNoticeText);


    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
