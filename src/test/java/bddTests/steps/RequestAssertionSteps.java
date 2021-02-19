package bddTests.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.dto.UserCreationError;
import redmine.model.dto.UserDto;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;

import static redmine.utils.Asserts.assertEquals;
import static redmine.utils.gson.GsonHelper.getGson;

public class RequestAssertionSteps {

    @И("Получен статус код ответа {int}")
    public void assertAnswerCode(int expectedCode) {
        Response response = Context.get("response", Response.class);
        assertEquals(response.getStatusCode(), expectedCode);
    }

    @И("В базе данных появилась запись с данными {string}")
    public void assertUserCreationInDb(String userDataStashId) {
        User userContext = Context.get(userDataStashId, User.class);
        String query = String.format("select * from users where login='%s'", userContext.getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        assertEquals(dbUser.get("login"), userContext.getLogin());
        assertEquals(dbUser.get("firstname"), userContext.getFirstName());
        assertEquals(dbUser.get("lastname"), userContext.getLastName());
    }

    @То("Тело содержит данные созданного пользователя {string}")
    public void assertUserInformationExist(String userStashDto) {
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);

        Assert.assertNotNull(createdUser.getUser().getId());
        assertEquals(createdUser.getUser().getLogin(), userContext.getUser().getLogin());
        assertEquals(createdUser.getUser().getFirstname(), userContext.getUser().getFirstname());
        assertEquals(createdUser.getUser().getLastname(), userContext.getUser().getLastname());
        Assert.assertNull(createdUser.getUser().getPassword());
        assertEquals(createdUser.getUser().getMail(), userContext.getUser().getMail());
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        assertEquals(createdUser.getUser().getStatus(), userContext.getUser().getStatus());
        Assert.assertEquals(createdUser.getUser().getAdmin(), userContext.getUser().getAdmin());
    }

    @И("В базе данных появилась запись с данными пользователя {string}")
    public void assertUserCreationInDbDto(String userDataStashId) {
        UserDto userContext = Context.get(userDataStashId, UserDto.class);
        String query = String.format("select * from users where login='%s'", userContext.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        assertEquals(dbUser.get("login"), userContext.getUser().getLogin());
        assertEquals(dbUser.get("firstname"), userContext.getUser().getFirstname());
        assertEquals(dbUser.get("lastname"), userContext.getUser().getLastname());
        assertEquals(dbUser.get("status"), userContext.getUser().getStatus());
    }

    @То("Тело ответа содержит {int} ошибки,с текстом:{string},{string},{string}")
    public void errorsCheck(Integer errorNumber, String errorEmail, String errorLogin, String errorChars) {
        if (errorNumber == 2) {
            Response response = Context.get("response", Response.class);
            UserCreationError errors = getGson().fromJson(response.getBody().toString(), UserCreationError.class);
            assertEquals(errors.getErrors().size(), 2);
            assertEquals(errors.getErrors().get(0), "Email уже существует");
            assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
        }
        if (errorNumber == 3) {
            Response response = Context.get("response", Response.class);
            UserCreationError errors = getGson().fromJson(response.getBody().toString(), UserCreationError.class);
            assertEquals(errors.getErrors().size(), 3);
            assertEquals(errors.getErrors().get(0), "Email имеет неверное значение");
            assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
            assertEquals(errors.getErrors().get(2), "Пароль недостаточной длины (не может быть меньше 8 символа)");
        }
    }
    @То("В базе данных изменилась запись с данными пользователя {string}")
    public void dbCheckAfterPutRequest(String userStashDto){
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);
        String query = String.format("select * from users where login='%s'", userContext.getUser().getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Map<String, Object> dbUser = result.get(0);
        assertEquals(dbUser.get("status"),1 );
    }

    @То("В базе данных отсутствует информация о  пользователе {string} созданном {string}")
    public void dbCheckAfterDeleteRequest(String userStashDto,String stashId){
        UserDto userContext = Context.get(userStashDto, UserDto.class);
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        Integer userId = user.getId()+2;
        String uri = String.format("users/%d.json", userId);
        Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
        Response response = apiClient.executeRequest(request);
        UserDto userDto= response.getBody(UserDto.class);
        Context.put("response",response);
        assertEquals(response.getStatusCode(), 404);
    }
    @То("В теле содержиться информация пользователя {string} о самом себе, присутсутвуют поля admin и apikey")
    public void assertUserInformationFieldExist(String stashId) {
        User user = Context.get(stashId, User.class);
        Response response = Context.get("response", Response.class);
        UserDto createdUser = response.getBody(UserDto.class);

        Assert.assertNotNull(createdUser.getUser().getId());
        assertEquals(createdUser.getUser().getLogin(), user.getLogin());
        assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
        assertEquals(createdUser.getUser().getLastname(), user.getLastName());
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getAdmin(),user.getAdmin());
        Assert.assertEquals(createdUser.getUser().getApi_key(),user.getApiKey());
    }



}
