package hometests.api;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.requests.UserRequests;
import redmine.model.user.User;

import static io.restassured.RestAssured.given;

public class TestCase4 {
    User firstUser;
    User secondUser;

    @BeforeMethod
    public void prepareFixtures() {
        firstUser = new User().setAdmin(false).setStatus(1).generate();
        secondUser = new User().setAdmin(false).setStatus(1).generate();
    }

    @Test(testName = "Шаг 1-Удаление пользователя другим пользователем и проверка в бд ", priority = 5)
    public void userDeleteByOtherUser() {
        String firstUserApiKey = firstUser.getApiKey();
        Integer secondUserId = secondUser.getId();
        String uri = String.format("users/%d.json", secondUserId);
        int usersBeforeDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", firstUserApiKey)
                .request(Method.DELETE, uri);
        Assert.assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterDelete = UserRequests.getAllUsers().size();
        Assert.assertEquals(userCountAfterDelete, usersBeforeDelete);
    }

    @Test(testName = "Шаг 2 -Удаление пользователя самим собою и проверка в бд ", priority = 10)
    public void userDeleteByHimself() {
        Integer userId = firstUser.getId();
        String userApiKey = firstUser.getApiKey();
        String uri = String.format("users/%d.json", userId);
        int usersBeforeDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", userApiKey)
                .request(Method.DELETE, uri);
        Assert.assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterDelete = UserRequests.getAllUsers().size();
        Assert.assertEquals(userCountAfterDelete, usersBeforeDelete);
    }
}
