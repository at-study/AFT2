package bddTests.steps;

import cucumber.api.java.ru.И;
import org.testng.Assert;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.dto.UserDto;
import redmine.model.user.User;
import redmine.utils.Asserts;
import redmine.utils.gson.GsonHelper;
import java.util.List;
import java.util.Map;

public class RequestAssertionSteps {

    @И("Получен статус код ответа {int}")
    public void assertAnswerCode(int expectedCode) {
        Response response=Context.get("user_operation_response", Response.class);
        Asserts.assertEquals(response.getStatusCode(), expectedCode);
    }

    @И("В базе данных появилась запись с данными {string}")
    public void assertUserCreationInDb(String userDataStashId) {
        User userContext = Context.get(userDataStashId, User.class);
        String query = String.format("select * from users where login='%s'", userContext.getLogin());
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        Map<String, Object> dbUser = result.get(0);
        Asserts.assertEquals(dbUser.get("login"), userContext.getLogin());
        Asserts.assertEquals(dbUser.get("firstname"), userContext.getFirstName());
        Asserts.assertEquals(dbUser.get("lastname"), userContext.getLastName());
    }

    @И("В теле ответа содержится информация о пользователе {string} ,и присутствуют поля admin=false и apikey пользователя")
    public void assertInformationLocationInResponse(String stashId) {
        Response response = Context.get("user_operation_response", Response.class);
        User firstUser = Context.get(stashId, User.class);
        String responseBody = response.getBody().toString();
        UserDto createdGetUser = GsonHelper.getGson().fromJson(responseBody, UserDto.class);
        Asserts.assertEquals(createdGetUser.getUser().getId(), firstUser.getId());
        Asserts.assertEquals(createdGetUser.getUser().getLogin(), firstUser.getLogin());
        Asserts.assertEquals(createdGetUser.getUser().getFirstname(), firstUser.getFirstName());
        Asserts.assertEquals(createdGetUser.getUser().getLastname(), firstUser.getLastName());
        Assert.assertNull(createdGetUser.getUser().getCreated_on());
        Assert.assertNull(createdGetUser.getUser().getLast_login_on());

        Assert.assertFalse(createdGetUser.getUser().getAdmin());
        Asserts.assertEquals(createdGetUser.getUser().getApi_key(), firstUser.getApiKey());
    }
}
