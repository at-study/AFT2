package HOMETESTS.API.Suite1;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.user.User;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class Case2 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Тест на создание пользователя повторно ", priority = 5,
            description = "Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    public void repeatedUserCreationTest(){
    String login = randomEnglishLowerString(8);
    String firstName = randomEnglishLowerString(12);
    String lastName = randomEnglishLowerString(12);
    String mail = randomEmail();
    Integer status = 2;
    String body = String.format("{\n" +
            " \"user\":{\n" +
            " \"login\":\"%s\",\n" +
            " \"firstname\":\"%s\",\n" +
            " \"lastname\":\"%s\",\n" +
            " \"mail\":\"%s\",\n" +
            " \"status\":\"%s\",\n" +
            " \"password\":\"1qaz@WSX\" \n" +
            " }\n" +
            "}", login, firstName, lastName, mail, status);
    ApiClient apiClient = new RestApiClient(user);
    Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
    Response response = apiClient.executeRequest(request);
        /**
         * Повторный запрос и получение 422 ошибки
         */
    Response response2 = apiClient.executeRequest(request);
    Assert.assertEquals(response2.getStatusCode(), 422);}
}
