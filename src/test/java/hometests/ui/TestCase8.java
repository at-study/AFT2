package hometests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.*;
import redmine.utils.Asserts;

import java.util.List;
import java.util.Map;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase8 {
    private User userAdmin;

    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Создание пользователя", description = "Администрирование. Создание пользователя")
    @Description("8.Администрирование. Создание пользователя.")
    public void usersCreationForAdmin() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        Asserts.assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        Asserts.assertEquals(getPage(AdministrationPage.class).adminPageName(), "Администрирование");
        Asserts.assertEquals(getPage(AdministrationPage.class).users(), "Пользователи");
        getPage(AdministrationPage.class).users.click();
        Asserts.assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        Asserts.assertEquals(getPage(UsersPage.class).newUserAdd(), "Новый пользователь");
        getPage(UsersPage.class).addNewUser.click();
        Asserts.assertEquals(getPage(NewUserCreationPage.class).newUserPage(), "Пользователи » Новый пользователь");

        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();

        formFillAndNotice(login, firstName, lastName, mail);
        checkInDb(login, firstName, lastName, mail);
    }

    @Step("Заполнение формы и уведомление")
    private void formFillAndNotice(String login, String firstName, String lastName, String mail) {
        getPage(NewUserCreationPage.class).userCreation(login, firstName, lastName, mail);
        String flashNoticeText = String.format("Пользователь %s создан.", login);
        Assert.assertEquals(getPage(NewUserCreationPage.class).flashNotice(), flashNoticeText);
    }

    @Step("Проверка создания пользователя в БД")
    private void checkInDb(String login, String firstName, String lastName, String mail) {
        String query = String.format("select * from users u  inner join email_addresses e on u.id=e.user_id where login='%s'", login);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        dbUser.get("id");
        Asserts.assertEquals(dbUser.get("login"), login);
        Asserts.assertEquals(dbUser.get("firstname"), firstName);
        Asserts.assertEquals(dbUser.get("lastname"), lastName);
        Asserts.assertEquals(dbUser.get("address"), mail);
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
