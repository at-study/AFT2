package bddTests.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.model.user.User;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class RequestSteps {
    @И("У пользователя {string} есть доступ к API и ключ API")
    public static void apiAccess(String stashId) {
        ApiClient apiClient;
        User user = Context.get(stashId, User.class);
        apiClient = new RestApiClient(user);
        String apiKeyForQuery = user.getApiKey();
    }

    @Если("Отправить запрос на создание пользователя {string} пользователем {string} с параметрами:")
    public static Response answerOnUserCreationRequest(String stashId1, String stashId2) {
        User user = Context.get(stashId1, User.class);
        User creator = Context.get(stashId2, User.class);
        String apiKeyForQuery = creator.getApiKey();
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, mail, name, lastName, password);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        // apiClient.executeRequest(request);
        // Response userCreationByNonAdmin = apiClient.executeRequest(request);
        // Asserts.assertEquals(userCreationByNonAdmin.getStatusCode(), 403);
        return null;
    }
}
