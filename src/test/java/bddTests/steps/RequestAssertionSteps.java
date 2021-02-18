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

   @То("Тело содержит данные созданного пользователя {string}")
    public void assertUserInformationExist(String userStashDto) {
       UserDto userContext = Context.get(userStashDto,UserDto.class);
       Response response=Context.get("response",Response.class);
       UserDto createdUser = response.getBody(UserDto.class);

       Assert.assertNotNull(createdUser.getUser().getId());
       assertEquals(createdUser.getUser().getLogin(), userContext.getUser().getLogin());
       assertEquals(createdUser.getUser().getFirstname(), userContext.getUser().getFirstname());
       assertEquals(createdUser.getUser().getLastname(), userContext.getUser().getLastname());
       Assert.assertNull(createdUser.getUser().getPassword());
       assertEquals(createdUser.getUser().getMail(),userContext.getUser().getMail());
       Assert.assertNull(createdUser.getUser().getLast_login_on());
       assertEquals(createdUser.getUser().getStatus(), userContext.getUser().getStatus());
       Assert.assertEquals(createdUser.getUser().getAdmin(),userContext.getUser().getAdmin());
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
}
