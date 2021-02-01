package hometests.api;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.db.requests.UserRequests;
import redmine.model.user.User;
import redmine.utils.Asserts;
import static io.restassured.RestAssured.given;

public class ApiTestCase4 {
    private User firstUser;
    private User secondUser;

    @BeforeMethod
    public void prepareFixtures() {
        firstUser = new User().setAdmin(false).setStatus(1).generate();
        secondUser = new User().setAdmin(false).setStatus(1).generate();
    }

    @Test(testName = "4. Удаление пользователей. Пользователь без прав администратора ")
    public void deleteUsers() {
        userDeleteByOtherUser();
        userDeleteByHimself();
    }

    @Description("Отправить запрос DELETE на удаление пользователя из п.3, используя ключ из п.2. (удаление другого пользователя)")
    public void userDeleteByOtherUser() {
        String firstUserApiKey = firstUser.getApiKey();
        Integer secondUserId = secondUser.getId();
        String uri = String.format("users/%d.json", secondUserId);
        int userCountBeforeUserDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", firstUserApiKey)
                .request(Method.DELETE, uri);
        Asserts.assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterUserDelete = UserRequests.getAllUsers().size();
        Asserts.assertEquals(userCountAfterUserDelete, userCountBeforeUserDelete);
    }

    @Description("Отправить запрос DELETE на удаление пользователя из п.1, используя ключи из п.2 (удаление себя)")
    public void userDeleteByHimself() {
        Integer userId = firstUser.getId();
        String userApiKey = firstUser.getApiKey();
        String uri = String.format("users/%d.json", userId);
        int userCountBeforeUserDelete = UserRequests.getAllUsers().size();
        io.restassured.response.Response deleteResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", userApiKey)
                .request(Method.DELETE, uri);
        Asserts.assertEquals(deleteResponse.getStatusCode(), 403);
        int userCountAfterUserDelete = UserRequests.getAllUsers().size();
        Asserts.assertEquals(userCountAfterUserDelete, userCountBeforeUserDelete);
    }
}
