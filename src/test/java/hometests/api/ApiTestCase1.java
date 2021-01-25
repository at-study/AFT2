package hometests.api;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.db.requests.UserRequests;
import redmine.model.dto.UserCreationError;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.utils.StringGenerators;
import redmine.utils.gson.GsonHelper;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class ApiTestCase1 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setAdmin(true).setStatus(1).generate();
    }

    @Test(testName = "Шаг-1 Тест на создание пользователя ", priority = 5,
            description = "Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    @Description("1. Отправить запрос POST на создание пользователя")
    public void testUserCreation() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        Integer status = 2;
        String body = String.format("{\n" +
                        "         \"user\":{\n" +
                        "         \"login\":\"%s\",\n" +
                        "         \"firstname\":\"%s\",\n" +
                        "         \"lastname\":\"%s\",\n" +
                        "         \"mail\":\"%s\",\n" +
                        "         \"status\":\"%s\",\n" +
                        "         \"password\":\"1qaz@WSX\"\n" +
                        "         }\n" +
                        "        }"
                , login, firstName, lastName, mail, status);
        int usersBeforeUserCreation = UserRequests.getAllUsers().size();
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        assertEquals(response.getStatusCode(), 201);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        assertEquals(createdUser.getUser().getLogin(), login);
        assertEquals(createdUser.getUser().getFirstname(), firstName);
        assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        assertEquals(createdUser.getUser().getStatus().intValue(), 2);
        Assert.assertFalse(createdUser.getUser().getAdmin());

        int usersCountAfter = UserRequests.getAllUsers().size();
        Assert.assertEquals(usersCountAfter, usersBeforeUserCreation + 1);
        int idForCheck = createdUser.getUser().getId();
        user.setId(idForCheck);
        User dataBaseUser = UserRequests.getUser(user);
        Assert.assertEquals(dataBaseUser.getStatus().toString(), "2");
    }

    @Test(testName = "Шаг-2 Тест на создание пользователя повторно ", priority = 7,
            description = "Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    @Description("2. Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    public void repeatedUserCreationTest() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String password = randomEnglishLowerString(8);
        Integer status = 2;
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"status\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, status, password);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        apiClient.executeRequest(request);
        Response sameUserCreationRequest = apiClient.executeRequest(request);
        assertEquals(sameUserCreationRequest.getStatusCode(), 422);

        UserCreationError errors = GsonHelper.getGson().fromJson(sameUserCreationRequest.getBody().toString(), UserCreationError.class);
        assertEquals(errors.getErrors().size(), 2);
        assertEquals(errors.getErrors().get(0), "Email уже существует");
        assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
    }

    @Test(testName = "Шаг-3 Тест на создание пользователя повторно с почти тем же запросом ", priority = 9,
            description = "Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    @Description("3. Отправить запрос POST на создание пользователя повторно с тем же телом запроса(C ошибками)")
    public void repeatedUserCreationTestWithSpecialErrors() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String incorrectMail = "santa.claus.petersburg";
        String password = StringGenerators.randomEnglishString(10);
        String incorrectPassword = String.valueOf(new Random().nextInt(500000) + 100000);
        String body = String.format(" {\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, password);
        String incorrectBody = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, incorrectMail, incorrectPassword);
        ApiClient apiClient = new RestApiClient(user);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        apiClient.executeRequest(request);
        Request incorrectRequest = new RestRequest("users.json", HttpMethods.POST, null, null, incorrectBody);
        Response sameUserCreationRequest = apiClient.executeRequest(incorrectRequest);
        assertEquals(sameUserCreationRequest.getStatusCode(), 422);
        UserCreationError errors = GsonHelper.getGson().fromJson(sameUserCreationRequest.getBody().toString(), UserCreationError.class);
        assertEquals(errors.getErrors().size(), 3);
        assertEquals(errors.getErrors().get(0), "Email имеет неверное значение");
        assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        assertEquals(errors.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

    @Test(testName = "Шаг-4 Изменение статуса у существующего ", priority = 11,
            description = "Отправить запрос PUT на изменение пользователя. Использовать данные из ответа запроса, выполненного в шаге №1, но при этом изменить поле status = 1")
    @Description("4. Отправить запрос PUT на изменение пользователя. ")
    public void testStatusChange() {
        String login = randomEnglishLowerString(8);
        String firstName = "Evgeny" + randomEnglishLowerString(6);
        String lastName = "TT" + randomEnglishLowerString(10);
        String mail = randomEmail();
        Integer status = 2;
        Integer putStatus = 1;
        String password = StringGenerators.randomEnglishString(10);
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"status\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, status, password);

        String statusBody = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"status\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, putStatus, password);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        String responseBody = response.getBody().toString();
        UserDto createdUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Integer userId = createdUser.getUser().getId();
        assertEquals(createdUser.getUser().getStatus().intValue(), 2);
        System.out.println("Created userID: " + userId);

        String uri = String.format("users/%d.json", userId);
        int userBeforeManipulation = UserRequests.getAllUsers().size();
        Request putRequest = new RestRequest(uri, HttpMethods.PUT, null, null, statusBody);
        Response putResponse = apiClient.executeRequest(putRequest);
        assertEquals(putResponse.getStatusCode(), 204);
        int userCountAfterManipulation = UserRequests.getAllUsers().size();
        Assert.assertEquals(userCountAfterManipulation, userBeforeManipulation);
        user.setId(userId);
        User dataBaseUser = UserRequests.getUser(user);
        Assert.assertEquals(dataBaseUser.getStatus().toString(), "1");
    }

    @Test(testName = "Шаг-5 Отправить запрос GET на получение пользователя ", priority = 13,
            description = "Отправить запрос GET на получение пользователя")
    @Description("5. Отправить запрос GET на получение пользователя")
    public void testGetRequest() {
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate" + randomEnglishLowerString(4);
        String lastName = "AndGet" + randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"status\":\"%s\",\n" +
                "                 \"password\":\"1qaz@WSX\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, status);

        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        assertEquals(response.getStatusCode(), 201);

        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        assertEquals(createdUser.getUser().getLogin(), login);
        assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Integer userId = createdUser.getUser().getId();
        System.out.println("Created userID: " + userId);

        String uri = String.format("users/%d.json", userId);
        Request getRequest = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response getResponse = apiClient.executeRequest(getRequest);
        assertEquals(getResponse.getStatusCode(), 200);
        UserDto createdGetUser = getResponse.getBody(UserDto.class);
        Assert.assertNotNull(createdGetUser.getUser().getId());
        assertEquals(createdGetUser.getUser().getLogin(), login);
        assertEquals(createdGetUser.getUser().getFirstname(), firstName);
        assertEquals(createdGetUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdGetUser.getUser().getPassword());
        assertEquals(createdGetUser.getUser().getMail(), mail);
        Assert.assertNull(createdGetUser.getUser().getLast_login_on());
        assertEquals(createdGetUser.getUser().getStatus().intValue(), 1);
    }

    @Test(testName = "Шаг-6 Отправить запрос DELETE на удаление пользователя ", priority = 15,
            description = "Отправить запрос DELETE на удаление пользователя")
    @Description("6. Отправить запрос DELETE на удаление пользователя")
    public void testDeleteRequest() {
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate" + randomEnglishLowerString(4);
        String lastName = "AndDelete" + randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"status\":\"%s\",\n" +
                "                 \"password\":\"1qaz@WSX\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, status);

        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        assertEquals(response.getStatusCode(), 201);
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        assertEquals(createdUser.getUser().getLogin(), login);
        assertEquals(createdUser.getUser().getFirstname(), firstName);
        assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Integer userId = createdUser.getUser().getId();
        System.out.println("Created userID: " + userId);

        int userAmountBeforeDeleteOtherUser = UserRequests.getAllUsers().size();
        String uri = String.format("users/%d.json", userId);
        Request deleteRequest = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response deleteResponse = apiClient.executeRequest(deleteRequest);
        assertEquals(deleteResponse.getStatusCode(), 204);
        int userAmountAfterDeleteOtherUser = UserRequests.getAllUsers().size();
        Assert.assertEquals(userAmountAfterDeleteOtherUser, userAmountBeforeDeleteOtherUser - 1);
    }

    @Test(testName = "Шаг 7-Отправить повторный запрос DELETE на удаление пользователя ", priority = 17,
            description = "Отправить повторный запрос DELETE на удаление пользователя")
    @Description("7. Отправить запрос DELETE на удаление пользователя (повторно)")
    public void testRepeatDeleteRequest() {
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate" + randomEnglishLowerString(4);
        String lastName = "AndDelete" + randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"status\":\"%s\",\n" +
                "                 \"password\":\"1qaz@WSX\"\n" +
                "                 }\n" +
                "                }", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        assertEquals(response.getStatusCode(), 201);

        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        assertEquals(createdUser.getUser().getLogin(), login);
        assertEquals(createdUser.getUser().getFirstname(), firstName);
        assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Integer userId = createdUser.getUser().getId();
        System.out.println("Created userID: " + userId);

        String uri = String.format("users/%d.json", userId);
        Request deleteRequest = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response deleteResponse = apiClient.executeRequest(deleteRequest);
        assertEquals(deleteResponse.getStatusCode(), 204);

        Response deleteRepeatedResponse = apiClient.executeRequest(deleteRequest);
        assertEquals(deleteRepeatedResponse.getStatusCode(), 404);
    }
}
