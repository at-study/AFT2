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
import redmine.model.Dto.UserCreationError;
import redmine.model.user.User;
import redmine.utils.gson.GsonHelper;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class Case3 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Тест на создание пользователя повторно-INVALID PASS and mail ", priority = 5,
            description = "Отправить запрос POST на создание пользователя повторно с тем же телом запроса")
    public void repeatedUserCreationTest() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String mail2="adda.gavgavgav";
        Integer status = 2;
        String password=randomEnglishLowerString(10);
        String password2=randomEnglishLowerString(6);
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"status\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, status,password);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        /**
         * Повторный запрос и получение 422 ошибки
         */
        Request request2 = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response2 = apiClient.executeRequest(request2);
        Assert.assertEquals(response2.getStatusCode(), 422);
        /**
         * Тело ответа содержит "errors", содержащий строки: "Email has already been taken", "Login has already been taken"
         */
        UserCreationError errors = GsonHelper.getGson().fromJson(response2.getBody().toString(), UserCreationError.class);
        Assert.assertEquals(errors.getErrors().size(), 2);
        Assert.assertEquals(errors.getErrors().get(0), "Email уже существует");
        Assert.assertEquals(errors.getErrors().get(1), "Пользователь уже существует");
    }
}
