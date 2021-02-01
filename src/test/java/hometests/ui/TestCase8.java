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
import redmine.utils.BrowserUtils;
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

    @Test(testName = " Администрирование. Создание пользователя",description = " Администрирование. Создание пользователя")
    @Description("8.Администрирование. Создание пользователя.")
    public void userCreationByAdmin() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        displayOfHomePage();
        displayOfAdministrationPage();
        displayOfNewUserCreationPage();
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        assertFormAndNotice(login, firstName, lastName, mail);
        assertUserCreationInDb(login, firstName, lastName, mail);
    }

    @Step("1. Отображается домашняя страница")
    private void displayOfHomePage(){
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).adminHomePage));
    }

    @Step("2. Отображается страница \"Администрирование\"")
    private void displayOfAdministrationPage(){
        getPage(HeaderPage.class).administration.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(AdministrationPage.class).adminPageName));
    }

    @Step("3.  Отображается страница \"Пользователи >> Новый пользователь\"")
    private void displayOfNewUserCreationPage(){
        getPage(AdministrationPage.class).users.click();
        getPage(UsersPage.class).addNewUser.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(NewUserCreationPage.class).newUserCreationPage));

    }

    @Step("4.Отображается сообщение 'Пользователь <логин> создан'.")
    private void assertFormAndNotice(String login, String firstName, String lastName, String mail) {
        getPage(NewUserCreationPage.class).userCreation(login, firstName, lastName, mail);
        String flashNoticeText = String.format("Пользователь %s создан.", login);
        Assert.assertEquals(getPage(NewUserCreationPage.class).flashNoticeAboutNewUSerCreation(), flashNoticeText);
    }

    @Step("5.В базе данных появилась в таблице users появилась запись с данными пользователями из п.4")
    private void assertUserCreationInDb(String login, String firstName, String lastName, String mail) {
        String query = String.format("select * from users u  inner join email_addresses e on u.id=e.user_id where login='%s'", login);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
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
