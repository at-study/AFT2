package HOMETESTS.API;
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
        Integer userId=createdInfoUser.getUser().getId();
        String userApiKey=createdInfoUser.getUser().getApi_key();
        System.out.println("Created userID 1: "+userId);
        String uri = String.format("users/%d.json",userId);
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
        UserDto createdFirstUser = createResponse.getBody(UserDto.class);
        Assert.assertNotNull(createdFirstUser.getUser().getId());
        Integer userId=createdFirstUser.getUser().getId();
        String userApiKey=createdFirstUser.getUser().getApi_key();
        System.out.println("Created userID 1: "+userId);

        String loginSecondUser = randomEnglishLowerString(8);
        String firstNameSecondUser = randomEnglishLowerString(12);
        String lastNameSecondUser = randomEnglishLowerString(12);
        String mailSecondUser = randomEmail();
        Integer statusSecondUser = 2;
        String bodySecondUser = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"1qaz@WSX"\s
                 }
                }""", loginSecondUser, firstNameSecondUser, lastNameSecondUser, mailSecondUser, statusSecondUser);
        ApiClient apiClientSecondUser = new RestApiClient(user);
        Request createRequestSecondUser = new RestRequest("users.json", HttpMethods.POST, null, null, bodySecondUser);
        Response createResponseSecondUser = apiClient.executeRequest(createRequestSecondUser);
        Assert.assertEquals(createResponseSecondUser.getStatusCode(), 201);
        UserDto createdSecondUser = createResponseSecondUser.getBody(UserDto.class);
        Assert.assertNotNull(createdSecondUser.getUser().getId());
        Integer userIdSecondUser=createdSecondUser.getUser().getId();
        String userApiKeySecondUser=createdSecondUser.getUser().getApi_key();
        System.out.println("Created userID 2: "+userIdSecondUser);


        String uri = String.format("users/%d.json",userIdSecondUser);
        String token = String.valueOf(userApiKey);
        Request getRequest = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Map<String, String> headers = getRequest.getHeaders();
        headers.put("X-Redmine_API-Key", token);
        Response getResponse = apiClient.executeRequest(getRequest);
        assertEquals(getResponse.getStatusCode(), 200);


        String responseBody = getResponse.getBody().toString();
        UserDto createdGetUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Assert.assertEquals(createdGetUser.getUser().getLogin(), loginSecondUser);
        Assert.assertEquals(createdGetUser.getUser().getFirstname(), firstNameSecondUser);
        Assert.assertEquals(createdGetUser.getUser().getLastname(), lastNameSecondUser);
        Assert.assertNull(createdGetUser.getUser().getPassword());
        Assert.assertEquals(createdGetUser.getUser().getMail(), mailSecondUser);
        Assert.assertNull(createdGetUser.getUser().getLast_login_on());
        Assert.assertEquals(createdGetUser.getUser().getStatus().intValue(), 2);
        Assert.assertNull(createdGetUser.getUser().getAdmin());
        Assert.assertNull(createdGetUser.getUser().getApi_key());


    }
}
