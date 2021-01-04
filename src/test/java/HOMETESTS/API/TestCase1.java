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
import redmine.model.Dto.UserCreationError;
import redmine.model.Dto.UserDto;
import redmine.model.user.User;
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

    @Test(testName = "Шаг 1(без подпункта 3)-Тест на создание пользователя ", priority = 5,
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
        String incorrectMail="dd.dd.petersburg";
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
        String password = String.valueOf(new Random().nextInt(500000) + 100000);
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, password);

        Response response = (Response) given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .body(body)
                .request(Method.POST, "users.json");

        UserCreationError errors = GsonHelper.getGson().fromJson(response.getBody().toString(), UserCreationError.class);
        Assert.assertEquals(errors.getErrors().size(), 3);
        Assert.assertEquals(errors.getErrors().get(0), "Пароль недостаточной длины (не может быть меньше 8 символа)");
        Assert.assertEquals(errors.getErrors().get(1), "Пароль недостаточной длины (не может быть меньше 8 символа)");
        Assert.assertEquals(errors.getErrors().get(3), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

}
