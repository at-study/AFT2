package hometests.api;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.requests.UserRequests;
import redmine.model.user.User;
import redmine.utils.Asserts;

import static io.restassured.RestAssured.given;
import static redmine.utils.Asserts.*;

public class ApiTestCase4 {
    private User firstUser;
    private User secondUser;

    @BeforeMethod
    public void prepareFixtures() {
        firstUser = new User().setAdmin(false).setStatus(1).generate();
        secondUser = new User().setAdmin(false).setStatus(1).generate();
    }

    @Test(testName = "Шаг 1-Удаление пользователя другим пользователем и проверка в бд ", priority = 10, description = "1. Отправить запрос DELETE на удаление пользователя из п.3, используя ключ из п.2. (удаление другого пользователя)")
    @Description("1. Отправить запрос DELETE на удаление пользователя из п.3, используя ключ из п.2. (удаление другого пользователя)")
    public void userDeleteByOtherUser() {
        String firstUserApiKey = firstUser.getApiKey();
        Integer secondUserId = secondUser.getId();
        String uri = String.format("users/%d.json", secondUserId);
        int usersBeforeDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", firstUserApiKey)
                .request(Method.DELETE, uri);
        assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterDelete = UserRequests.getAllUsers().size();
        assertEquals(userCountAfterDelete, usersBeforeDelete);
    }

    @Test(testName = "Шаг 2 -Удаление пользователя самим собою и проверка в бд ", priority = 11, description = "2. Отправить запрос DELETE на удаление пользователя из п.1, используя ключи из п.2 (удаление себя)")
    @Description("2. Отправить запрос DELETE на удаление пользователя из п.1, используя ключи из п.2 (удаление себя)")
    public void userDeleteByHimself() {
        Integer userId = firstUser.getId();
        String userApiKey = firstUser.getApiKey();
        String uri = String.format("users/%d.json", userId);
        int usersBeforeDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", userApiKey)
                .request(Method.DELETE, uri);
        assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterDelete = UserRequests.getAllUsers().size();
        assertEquals(userCountAfterDelete, usersBeforeDelete);
    }
}
