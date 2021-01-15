package hometests;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.requests.UserRequests;
import redmine.model.user.User;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class TestCase4 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Шаг 1-Удаление пользователя другим пользователем и проверка в бд ")
    public void userDeleteByOtherUser() {
        Integer userId = 725;
        String userApiKey = "5aed704a56f9c2711d4cf2035a2d28a698b0cca1";
        Integer secondUserId = 726;
        String secondUserApiKey = "5f53e117604928097361205d1bba409b5c6211a4";
        String uri = String.format("users/%d.json", secondUserId);
        int usersBeforeDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", userApiKey)
                .request(Method.DELETE, uri);
        Assert.assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterDelete = UserRequests.getAllUsers().size();
        Assert.assertEquals(userCountAfterDelete, usersBeforeDelete);
    }

    @Test(testName = "Шаг 2 -Удаление пользователя самим собою и проверка в бд ")
    public void userDeleteByHimself() {
        Integer userId = 725;
        String userApiKey = "5aed704a56f9c2711d4cf2035a2d28a698b0cca1";
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
