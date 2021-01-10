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
import redmine.model.Dto.UserCreationError;
import redmine.model.Dto.UserDto;
import redmine.model.user.User;
import redmine.utils.StringGenerators;
import redmine.utils.gson.GsonHelper;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase1 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Шаг-1(без подпункта 3)-Тест на создание пользователя ", priority = 5,
            description = "Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    public void testUserCreation() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        Integer status = 2;
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"status\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(), 201);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Assert.assertEquals(createdUser.getUser().getLogin(), login);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 2);
        Assert.assertFalse(createdUser.getUser().getAdmin());
    }

    @Test(testName = "Шаг-2 Тест на создание пользователя повторно ", priority = 7,
            description = "Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    public void repeatedUserCreationTest() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String password = String.valueOf(new Random().nextInt(500000) + 100000);
        Integer status = 2;
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"status\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Response sameUserCreationRequest = apiClient.executeRequest(request);
        Assert.assertEquals(sameUserCreationRequest.getStatusCode(), 422);
        UserCreationError errors = GsonHelper.getGson().fromJson(sameUserCreationRequest.getBody().toString(), UserCreationError.class);
        Assert.assertEquals(errors.getErrors().size(), 2);
        Assert.assertEquals(errors.getErrors().get(0), "Email уже существует");
        Assert.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
    }

    @Test(testName = "Шаг-3 Тест на создание пользователя повторно с почти тем же запросом ", priority = 9,
            description = "Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    public void repeatedUserCreationTestWithSpecialErrors() {
        String apiKey = "f02b2da01a468c4116be898911481d1b928c15f9";
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String incorrectMail = "santa.claus.petersburg";
        String password = StringGenerators.randomEnglishString(10);
        String incorrectPassword = String.valueOf(new Random().nextInt(500000) + 100000);
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, password);
        String incorrectBody = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, firstName, lastName, incorrectMail, incorrectPassword);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Request incorrectRequest = new RestRequest("users.json", HttpMethods.POST, null, null, incorrectBody);
        Response sameUserCreationRequest = apiClient.executeRequest(incorrectRequest);
        Assert.assertEquals(sameUserCreationRequest.getStatusCode(), 422);
        UserCreationError errors = GsonHelper.getGson().fromJson(sameUserCreationRequest.getBody().toString(), UserCreationError.class);
        Assert.assertEquals(errors.getErrors().size(), 3);
        Assert.assertEquals(errors.getErrors().get(0), "Email имеет неверное значение");
        Assert.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        Assert.assertEquals(errors.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

    @Test(testName = "Шаг-4(без проверки)-Изменение статуса у существующего ", priority = 11,
            description = "Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1")
    public void testStatusChange() {
        String apiKey = "f2b07eec53f92b54a8522488ca25491167419076";
        String login = randomEnglishLowerString(8);
        String firstName = "Evgeny"+randomEnglishLowerString(6);
        String lastName = "TT"+randomEnglishLowerString(10);
        String mail = randomEmail();
        Integer status = 2;
        Integer putStatus=1;
        String password = StringGenerators.randomEnglishString(10);
        String body = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"%s"\s
                 }
                }""", login, firstName, lastName, mail, status,password);
        String statusBody = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"%s"\s
                 }
                }""", login, firstName, lastName,mail,putStatus,password );
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        String responseBody = response.getBody().toString();
        UserDto createdUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Integer userId=createdUser.getUser().getId();
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 2);
        System.out.println("Created userID: "+userId);
        String uri = String.format("users/%d.json",userId);
        Request putRequest = new RestRequest(uri, HttpMethods.PUT, null, null, statusBody);
        Response putResponse = apiClient.executeRequest(putRequest);
        Assert.assertEquals(putResponse.getStatusCode(), 204);
    }

    @Test(testName = "Шаг-5 Отправить запрос GET на получение пользователя ", priority = 13,
            description = "Отправить запрос GET на получение пользователя")
    public void testGetRequest() {
        String apiKey = "f2b07eec53f92b54a8522488ca25491167419076";
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate"+randomEnglishLowerString(4);
        String lastName = "AndGet"+randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"status\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(), 201);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Assert.assertEquals(createdUser.getUser().getLogin(), login);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Integer userId=createdUser.getUser().getId();
        System.out.println("Created userID: "+userId);
        String uri = String.format("users/%d.json",userId);
        Request getRequest = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response getResponse = apiClient.executeRequest(getRequest);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        UserDto createdGetUser = getResponse.getBody(UserDto.class);
        Assert.assertNotNull(createdGetUser.getUser().getId());
        Assert.assertEquals(createdGetUser.getUser().getLogin(), login);
        Assert.assertEquals(createdGetUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdGetUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdGetUser.getUser().getPassword());
        Assert.assertEquals(createdGetUser.getUser().getMail(), mail);
        Assert.assertNull(createdGetUser.getUser().getLast_login_on());
        Assert.assertEquals(createdGetUser.getUser().getStatus().intValue(), 1);
    }
    @Test(testName = "Шаг-6(Без проверки дб)Отправить запрос DELETE на удаление пользователя ", priority = 15,
            description = "Отправить запрос DELETE на удаление пользователя")
    public void testDeleteRequest() {
        String apiKey = "f2b07eec53f92b54a8522488ca25491167419076";
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate"+randomEnglishLowerString(4);
        String lastName = "AndDelete"+randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"status\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(), 201);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Assert.assertEquals(createdUser.getUser().getLogin(), login);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Integer userId=createdUser.getUser().getId();
        System.out.println("Created userID: "+userId);
        String uri = String.format("users/%d.json",userId);
        Request deleteRequest = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response deleteResponse = apiClient.executeRequest(deleteRequest);
        Assert.assertEquals(deleteResponse.getStatusCode(), 204);
    }

    @Test(testName = "Шаг-Отправить повторный запрос DELETE на удаление пользователя ", priority = 17,
            description = "Отправить повторный запрос DELETE на удаление пользователя")
    public void testRepeatDeleteRequest() {
        String apiKey = "f2b07eec53f92b54a8522488ca25491167419076";
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate"+randomEnglishLowerString(4);
        String lastName = "AndDelete"+randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"status\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(), 201);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Assert.assertEquals(createdUser.getUser().getLogin(), login);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Integer userId=createdUser.getUser().getId();
        System.out.println("Created userID: "+userId);
        String uri = String.format("users/%d.json",userId);
        Request deleteRequest = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response deleteResponse = apiClient.executeRequest(deleteRequest);
        Assert.assertEquals(deleteResponse.getStatusCode(), 204);
    }
}
