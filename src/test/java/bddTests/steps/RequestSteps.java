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
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;
import redmine.utils.Asserts;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;
import static redmine.utils.gson.GsonHelper.getGson;

public class RequestSteps {
    @И("У пользователя {string} есть {string} и ключ API")
    public static void apiAccess(String stashId, String apiClientStashId) {
        User apiUser = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(apiUser);
        Context.put(apiClientStashId, apiClient);
    }

    @Если("Отправить запрос на создание пользователя пользователем {string}")
    public static Response answerOnUserCreationRequest(String stashId, String apiClientStashId) {
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = Context.get(apiClientStashId, ApiClient.class);
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);
        Integer status = 1;
        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword(password));
        String body = getGson().toJson(userDto);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response userCreationByNonAdmin = apiClient.executeRequest(request);
        Asserts.assertEquals(userCreationByNonAdmin.getStatusCode(), 403);
        return null;
    }
}
