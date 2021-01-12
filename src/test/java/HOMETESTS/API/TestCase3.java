package HOMETESTS.API;

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
import redmine.model.Dto.UserDto;
import redmine.model.user.User;
import redmine.utils.gson.GsonHelper;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase3 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Шаг 1-Получение пользователем инфо о самом себе+допинфо ")
    public void userInfoAboutHimself() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        Integer status = 2;
        String body = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"1qaz@WSX"\s
                 }
                }""", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request createRequest = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response createResponse = apiClient.executeRequest(createRequest);
        Assert.assertEquals(createResponse.getStatusCode(), 201);
        UserDto createdInfoUser = createResponse.getBody(UserDto.class);
        Assert.assertNotNull(createdInfoUser.getUser().getId());
        Integer userId = createdInfoUser.getUser().getId();
        String userApiKey = createdInfoUser.getUser().getApi_key();
        System.out.println("Created userID 1: " + userId);
        String uri = String.format("users/%d.json", userId);
        Request getRequest = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response getResponse = apiClient.executeRequest(getRequest);
        assertEquals(getResponse.getStatusCode(), 200);
        String responseBody = getResponse.getBody().toString();
        UserDto createdGetUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Assert.assertEquals(createdGetUser.getUser().getLogin(), login);
        Assert.assertEquals(createdGetUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdGetUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdGetUser.getUser().getPassword());
        Assert.assertEquals(createdGetUser.getUser().getMail(), mail);
        Assert.assertNull(createdGetUser.getUser().getLast_login_on());
        Assert.assertEquals(createdGetUser.getUser().getStatus().intValue(), 2);
        Assert.assertFalse(createdGetUser.getUser().getAdmin());
        Assert.assertEquals(createdGetUser.getUser().getApi_key(), userApiKey);


    }

    @Test(testName = "Шаг 2-Получение пользователем инфо о другом пользователе +допинфо  ")
    public void userInfoAboutOtherUser() {
        Integer userId = 725;
        String userApiKey = "5aed704a56f9c2711d4cf2035a2d28a698b0cca1";
        Integer secondUserId = 726;
        String secondUserApiKey = "5f53e117604928097361205d1bba409b5c6211a4";

        String uri = String.format("users/%d.json", secondUserId);
        io.restassured.response.Response getResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", userApiKey)
                .request(Method.GET, uri);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        String responseBody = getResponse.getBody().asString();
        UserDto createdUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Assert.assertEquals(createdUser.getUser().getLogin(), "evgenyttuser2");
        Assert.assertEquals(createdUser.getUser().getFirstname(), "evgeny");
        Assert.assertEquals(createdUser.getUser().getLastname(), "tt");
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertNotNull(createdUser.getUser().getLast_login_on());
        Assert.assertNull(createdUser.getUser().getStatus());
        Assert.assertNull(createdUser.getUser().getAdmin());
        Assert.assertNull(createdUser.getUser().getApi_key());

    }
}
