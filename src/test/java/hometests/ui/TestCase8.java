package hometests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.interfaces.Response;
import redmine.db.requests.UserRequests;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.*;
import redmine.utils.Asserts;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Test(testName = " Администрирование. Создание пользователя", priority = 7, description = "Администрирование. Создание пользователя")
    @Description("8.Администрирование. Создание пользователя.")
    public void usersCreationForAdmin() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        Asserts.assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        Asserts.assertEquals(getPage(AdminPage.class).adminPageName(), "Администрирование");
        Asserts.assertEquals(getPage(AdminPage.class).users(), "Пользователи");
        getPage(AdminPage.class).users.click();
        Asserts.assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        Asserts.assertEquals(getPage(UsersPage.class).newUserAdd(), "Новый пользователь");
        getPage(UsersPage.class).newUserAdd.click();
        Asserts.assertEquals(getPage(UsersNewPage.class).newUserPage(), "Пользователи » Новый пользователь");

        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();

        getPage(UsersNewPage.class).userCreation(login, firstName, lastName, mail);
        String flashNoticeText = String.format("Пользователь %s создан.", login);
        Assert.assertEquals(getPage(UsersNewPage.class).flashNotice(), flashNoticeText);
        userCheckInDataBase(login);
    }
        @Step("Поверка создания пользователя в Базе Данных")
        private void userCheckInDataBase(String login){
        String quary=String.format("select * from users where login=%s",login);
        List<Map<String,Object>> quaryResult= Manager.dbConnection.executeQuery(quary);


        }
    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
