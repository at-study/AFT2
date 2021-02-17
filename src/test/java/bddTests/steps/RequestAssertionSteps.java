package bddTests.steps;

import cucumber.api.java.ru.И;
import org.testng.Assert;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.utils.Asserts;
import java.util.List;
import java.util.Map;

public class RequestAssertionSteps {
    @И("Получен статус код ответа {int}")
    public void assertAnswerCode(int expectedCode) {
        Response response=Context.get("response",Response.class);
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
}
