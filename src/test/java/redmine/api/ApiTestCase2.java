package redmine.api;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;
import redmine.utils.Asserts;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;
import static redmine.utils.gson.GsonHelper.getGson;

public class ApiTestCase2 {
    private ApiClient apiClient;

    @BeforeMethod
    public void prepareFixtures() {
        User user = new User().setAdmin(false).setStatus(1).generate();
        apiClient = new RestApiClient(user);
    }

    @Test(testName = "2. Создание пользователя. Пользователь без прав администратора ", description = "Создание пользователя. Пользователь без прав администратора")
    public void userCreationByNonAdmin() {
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);

        UserDto user = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                .setLastname(lastName).setMail(mail).setPassword(password));
        String body = getGson().toJson(user);

        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        apiClient.executeRequest(request);
        Response userCreationByNonAdmin = apiClient.executeRequest(request);
        Asserts.assertEquals(userCreationByNonAdmin.getStatusCode(), 403);
    }
}
