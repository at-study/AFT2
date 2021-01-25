package hometests.api;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.utils.gson.GsonHelper;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class ApiTestCase3 {
    User firstUser;
    User secondUser;

    @BeforeMethod
    public void prepareFixtures() {
        firstUser = new User().setAdmin(false).setStatus(1).generate();
        secondUser = new User().setAdmin(false).setStatus(1).generate();
    }

    @Test(testName = "Шаг 1-Получение пользователем инфо о самом себе+допинфо ",description = "1. Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2 ")
    @Description("1. Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2 ")
    public void userInfoAboutHimself() {
        ApiClient apiClient = new RestApiClient(firstUser);
        String uri = String.format("users/%d.json", firstUser.getId());
        Request getRequest = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response getResponse = apiClient.executeRequest(getRequest);
        assertEquals(getResponse.getStatusCode(), 200);

        String responseBody = getResponse.getBody().toString();
        UserDto createdGetUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Assert.assertEquals(createdGetUser.getUser().getId(), firstUser.getId());
        Assert.assertEquals(createdGetUser.getUser().getLogin(), firstUser.getLogin());
        Assert.assertEquals(createdGetUser.getUser().getFirstname(), firstUser.getFirstName());
        Assert.assertEquals(createdGetUser.getUser().getLastname(), firstUser.getLastName());
        Assert.assertNull(createdGetUser.getUser().getCreated_on());
        Assert.assertNull(createdGetUser.getUser().getLast_login_on());

        Assert.assertFalse(createdGetUser.getUser().getAdmin());
        Assert.assertEquals(createdGetUser.getUser().getApi_key(), firstUser.getApiKey());
    }

    @Test(testName = "Шаг 2-Получение пользователем инфо о другом пользователе +допинфо",description = "1. Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2 ")
    @Description("1. Отправить запрос GET на получение пользователя из п.1, используя ключ API из п.2 ")
    public void userInfoAboutOtherUser() {
        String uri = String.format("users/%d.json", secondUser.getId());
        io.restassured.response.Response getResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", firstUser.getApiKey())
                .request(Method.GET, uri);

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        String responseBody = getResponse.getBody().asString();
        UserDto createdUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Assert.assertEquals(createdUser.getUser().getLogin(), secondUser.getLogin());
        Assert.assertEquals(createdUser.getUser().getFirstname(), secondUser.getFirstName());
        Assert.assertEquals(createdUser.getUser().getLastname(), secondUser.getLastName());
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertNull(createdUser.getUser().getStatus(), secondUser.getStatus().toString());

        Assert.assertNull(createdUser.getUser().getAdmin());
        Assert.assertNull(createdUser.getUser().getApi_key());
    }
}
