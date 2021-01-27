package hometests.ui;

import io.qameta.allure.Description;
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
    private User user;

    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        User user=new User().setAdmin(false).setStatus(1).generate();
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


        String query = String.format("select * from users u inner join tokens t on u.id=t.user_id inner join email_addresses e on u.id=e.user_id where login='%s'", login);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);

    }

    @AfterMethod
    public void tearDown(){
        driverQuit();
    }
}
