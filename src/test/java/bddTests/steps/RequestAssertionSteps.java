package bddTests.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.dto.UserDto;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;

import static redmine.utils.Asserts.assertEquals;

public class RequestAssertionSteps {
    @И("Получен статус код ответа {int}")
    public void assertAnswerCode(int expectedCode) {
        Response response=Context.get("response",Response.class);
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
   @То("Тело содержит данные пользователя {string}")
    public void assertUserInformationExist(String stashId) {
       User user = Context.get(stashId, User.class);
       Response response=Context.get("response",Response.class);
       UserDto createdUser = response.getBody(UserDto.class);
       Assert.assertNotNull(createdUser.getUser().getId());
       assertEquals(createdUser.getUser().getLogin(), user.getLogin());
       assertEquals(createdUser.getUser().getFirstname(), user.getFirstName());
       assertEquals(createdUser.getUser().getLastname(), user.getLastName());
   }
}
