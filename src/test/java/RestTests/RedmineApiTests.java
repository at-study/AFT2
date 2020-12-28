package RestTests;

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

public class RedmineApiTests {
    User user;

    @BeforeMethod
    public void prepareFixtures(){
        user=new User().generate();
    }
    @Test(testName = "Тест получения  роли ")
    public void testRoleGet(){
        ApiClient apiClient=new RestApiClient(user);
        Request request=new RestRequest("roles.json", HttpMethods.GET,null,null,null);
        Response response= apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getHeaders().containsKey("Content-Type"));
    }

    @Test(testName = "Тест на создание пользователя ")
    public void testUserCreation(){
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail);
        ApiClient apiClient=new RestApiClient(user);
        Request request=new RestRequest("users.json", HttpMethods.POST,null,null,body);
        Response response= apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(),201);
        UserDto createdUser=response.getBody(UserDto.class);
        Assert.assertEquals(createdUser.getUser().getLogin(), login);
        Assert.assertEquals(createdUser.getUser().getFirstname(), firstName);
        Assert.assertEquals(createdUser.getUser().getLastname(), lastName);
        Assert.assertNull(createdUser.getUser().getPassword());
        Assert.assertEquals(createdUser.getUser().getMail(), mail);
        Assert.assertNull(createdUser.getUser().getLast_login_on());
        Assert.assertEquals(createdUser.getUser().getStatus().intValue(), 1);
        Assert.assertFalse(createdUser.getUser().getAdmin());
    }

}
