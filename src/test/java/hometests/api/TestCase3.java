package hometests.api;

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
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase3 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setAdmin(false).generate();
    }

    @Test(testName = "Шаг 1-Получение пользователем инфо о самом себе+допинфо ")
    public void userInfoAboutHimself() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        Integer status = 1;
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
        String login1 = randomEnglishLowerString(8);
        String firstName1 = randomEnglishLowerString(12);
        String lastName1 = randomEnglishLowerString(12);
        String mail1 = randomEmail();
        Integer status1 = 1;
        String body1 = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "admin":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"1qaz@WSX"\s
                 }
                }""", login1, firstName1, lastName1,true, mail1, status1);
        ApiClient apiClient = new RestApiClient(user);
        Request createRequest = new RestRequest("users.json", HttpMethods.POST, null, null, body1);
        Response createResponse = apiClient.executeRequest(createRequest);
        Assert.assertEquals(createResponse.getStatusCode(), 201);
        UserDto createdInfoUser = createResponse.getBody(UserDto.class);
        Assert.assertNotNull(createdInfoUser.getUser().getId());
        Integer userId = createdInfoUser.getUser().getId();
        String userApiKey1 = createdInfoUser.getUser().getApi_key();
        System.out.println("Created userID 1: " + userId);

        String login2 = randomEnglishLowerString(8);
        String firstName2 = randomEnglishLowerString(12);
        String lastName2 = randomEnglishLowerString(12);
        String mail2 = randomEmail();
        Integer status2 = 1;
        String body2 = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "admin":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"1qaz@WSX"\s
                 }
                }""", login2, firstName2, lastName2,true, mail2, status2);
        ApiClient apiClient2 = new RestApiClient(user);
        Request createRequest2 = new RestRequest("users.json", HttpMethods.POST, null, null, body2);
        Response createResponse2 = apiClient.executeRequest(createRequest2);
        Assert.assertEquals(createResponse2.getStatusCode(), 201);
        UserDto createdInfoUser2 = createResponse2.getBody(UserDto.class);
        Integer userId2 = createdInfoUser2.getUser().getId();
        System.out.println("Created userID 2: " + userId2);

        String uri = String.format("users/%d.json", userId2);
        io.restassured.response.Response getResponse = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", userApiKey1)
                .request(Method.GET, uri);
        Assert.assertEquals(getResponse.getStatusCode(), 403);
        String responseBody = getResponse.getBody().asString();
        UserDto createdUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Assert.assertEquals(createdUser.getUser().getLogin(), login2);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName2);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName2);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertNotNull(createdUser.getUser().getLast_login_on());
        Assert.assertNull(createdUser.getUser().getStatus());
        Assert.assertNull(createdUser.getUser().getAdmin());
        Assert.assertNull(createdUser.getUser().getApi_key());
    }
}
