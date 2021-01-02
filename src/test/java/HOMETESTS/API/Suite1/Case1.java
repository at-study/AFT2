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
import redmine.model.Dto.UserDto;
import redmine.model.user.User;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class Case1 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Тест на создание пользователя ", priority = 5,
            description = "Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    public void testUserCreation() {
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
        Assert.assertEquals(response.getStatusCode(), 201);
        /**
         * 2.тело ответа содержит данные пользователя в том числе его ид
         */
        UserDto createdUser = response.getBody(UserDto.class);
        Assert.assertNotNull(createdUser.getUser().getId());
        Assert.assertEquals(createdUser.getUser().getLogin(), login);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 2);
        Assert.assertFalse(createdUser.getUser().getAdmin());
        /**
         * 3. В базе данных есть информация о созданном пользователе, status = 2
         */

    }

}
