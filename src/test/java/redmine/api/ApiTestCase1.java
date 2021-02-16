package redmine.api;

import io.qameta.allure.Step;
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
import redmine.model.dto.UserInfo;
import redmine.model.user.User;
import redmine.utils.StringGenerators;

import static redmine.utils.Asserts.assertEquals;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;
import static redmine.utils.gson.GsonHelper.getGson;

public class ApiTestCase1 {
    private User user;
    private ApiClient apiClient;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setAdmin(true).setStatus(1).generate();
        apiClient = new RestApiClient(user);
    }

    @Test(testName = "1. Создание, изменение, получение, удаление пользователя. Администратор системы", description = "Создание, изменение, получение, удаление пользователя. Администратор системы")
    public void operationsWithUserByAdmin() {
        testUserCreation();
        testRepeatedUserCreation();
        testRepeatedUserCreationWithSpecialErrors();
        testStatusChange();
        testGetRequest();
        testDeleteRequest();
        testRepeatedDeleteRequest();
    }

    @Step("1. Отправить запрос POST на создание пользователя")
    public void testUserCreation() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        Integer status = 2;
        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword("1qaz@WSX"));
        String body = getGson().toJson(userDto);
        int usersCountBeforeUserCreation = UserRequests.getAllUsers().size();
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
        assertEquals(createdUser.getUser().getStatus(), 2);
        Assert.assertFalse(createdUser.getUser().getAdmin());

        int usersCountAfterUserCreation = UserRequests.getAllUsers().size();
        assertEquals(usersCountAfterUserCreation, usersCountBeforeUserCreation + 1);
        int createdUserId = createdUser.getUser().getId();
        user.setId(createdUserId);
        User dbUser = UserRequests.getUser(user);
        assertEquals(dbUser.getStatus().toString(), "2");
    }

    @Step("2. Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    public void testRepeatedUserCreation() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String password = randomEnglishLowerString(8);
        Integer status = 2;

        UserDto user = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword(password));
        String body = getGson().toJson(user);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        apiClient.executeRequest(request);
        Response sameUserCreationRequest = apiClient.executeRequest(request);
        assertEquals(sameUserCreationRequest.getStatusCode(), 422);

        UserCreationError errors = getGson().fromJson(sameUserCreationRequest.getBody().toString(), UserCreationError.class);
        assertEquals(errors.getErrors().size(), 2);
        assertEquals(errors.getErrors().get(0), "Email уже существует");
        assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
    }

    @Step("3. Отправить запрос POST на создание пользователя повторно с тем же телом запроса(C ошибками)")
    public void testRepeatedUserCreationWithSpecialErrors() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String incorrectMail = "santa.claus.petersburg";
        String password = StringGenerators.randomEnglishString(10);
        String incorrectPassword = randomEnglishLowerString(4);

        UserDto user = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(randomEmail()).setPassword(password));
        String body = getGson().toJson(user);
        UserDto incorrectUser = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(incorrectMail).setPassword(incorrectPassword));
        String incorrectBody = getGson().toJson(incorrectUser);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        apiClient.executeRequest(request);
        Request incorrectRequest = new RestRequest("users.json", HttpMethods.POST, null, null, incorrectBody);
        Response sameUserCreationRequest = apiClient.executeRequest(incorrectRequest);
        assertEquals(sameUserCreationRequest.getStatusCode(), 422);
        UserCreationError errors = getGson().fromJson(sameUserCreationRequest.getBody().toString(), UserCreationError.class);
        assertEquals(errors.getErrors().size(), 3);
        assertEquals(errors.getErrors().get(0), "Email имеет неверное значение");
        assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        assertEquals(errors.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
    }

    @Step("4. Отправить запрос PUT на изменение пользователя. ")
    public void testStatusChange() {
        String login = randomEnglishLowerString(8);
        String firstName = "Evgeny" + randomEnglishLowerString(6);
        String lastName = "TT" + randomEnglishLowerString(10);
        String mail = randomEmail();
        Integer status = 2;
        Integer putStatus = 1;
        String password = StringGenerators.randomEnglishString(10);

        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword(password));
        String body = getGson().toJson(userDto);
        UserDto userDto2 = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(putStatus).setPassword(password));
        String statusBody = getGson().toJson(userDto2);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        String responseBody = response.getBody().toString();
        UserDto createdUser = getGson().fromJson(responseBody, UserDto.class);
        Integer userId = createdUser.getUser().getId();
        assertEquals(createdUser.getUser().getStatus(), 2);
        System.out.println("Created userID: " + userId);

        String uri = String.format("users/%d.json", userId);
        int userBeforeManipulation = UserRequests.getAllUsers().size();
        Request putRequest = new RestRequest(uri, HttpMethods.PUT, null, null, statusBody);
        Response putResponse = apiClient.executeRequest(putRequest);
        assertEquals(putResponse.getStatusCode(), 204);
        int userCountAfterManipulation = UserRequests.getAllUsers().size();
        assertEquals(userCountAfterManipulation, userBeforeManipulation);
        user.setId(userId);
        User dataBaseUser = UserRequests.getUser(user);
        assertEquals(dataBaseUser.getStatus().toString(), "1");
    }

    @Step("5. Отправить запрос GET на получение пользователя")
    public void testGetRequest() {
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate" + randomEnglishLowerString(4);
        String lastName = "AndGet" + randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;

        UserDto user = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword("1qaz@WSX"));
        String body = getGson().toJson(user);
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
        assertEquals(createdUser.getUser().getStatus(), 1);
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
        assertEquals(createdGetUser.getUser().getStatus(), 1);
    }

    @Step("6. Отправить запрос DELETE на удаление пользователя")
    public void testDeleteRequest() {
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate" + randomEnglishLowerString(4);
        String lastName = "AndDelete" + randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;
        UserDto user = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword("1qaz@WSX"));
        String body = getGson().toJson(user);

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
        assertEquals(createdUser.getUser().getStatus(), 1);
        Integer userId = createdUser.getUser().getId();
        System.out.println("Created userID: " + userId);

        int userCountBeforeDeleteOtherUser = UserRequests.getAllUsers().size();
        String uri = String.format("users/%d.json", userId);
        Request deleteRequest = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response deleteResponse = apiClient.executeRequest(deleteRequest);
        assertEquals(deleteResponse.getStatusCode(), 204);
        int userAmountAfterDeleteOtherUser = UserRequests.getAllUsers().size();
        assertEquals(userAmountAfterDeleteOtherUser, userCountBeforeDeleteOtherUser - 1);
    }

    @Step("7. Отправить запрос DELETE на удаление пользователя (повторно)")
    public void testRepeatedDeleteRequest() {
        String login = randomEnglishLowerString(8);
        String firstName = "UserCreate" + randomEnglishLowerString(4);
        String lastName = "AndDelete" + randomEnglishLowerString(6);
        String mail = randomEmail();
        Integer status = 1;

        UserDto user = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(firstName)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword("1qaz@WSX"));
        String body = getGson().toJson(user);
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
        assertEquals(createdUser.getUser().getStatus(), 1);
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
