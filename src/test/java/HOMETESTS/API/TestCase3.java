package HOMETESTS.API;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
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
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase3 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }

    @Test(testName = "Шаг 1-Получение пользователем инфо о самом себе+допинфо ")
    public void userInfoAboutHimself() {
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        Integer status = 2;
        String body = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "mail":"%s",
                 "status":"%s",
                 "password":"1qaz@WSX"\s
                 }
                }""", login, firstName, lastName, mail, status);
        ApiClient apiClient = new RestApiClient(user);
        Request createRequest = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response createResponse = apiClient.executeRequest(createRequest);
        Assert.assertEquals(createResponse.getStatusCode(), 201);
        UserDto createdInfoUser = createResponse.getBody(UserDto.class);
        Assert.assertNotNull(createdInfoUser.getUser().getId());
        Integer userId=createdInfoUser.getUser().getId();
        System.out.println("Created userID: "+userId);
        String uri = String.format("users/%d.json",userId);
    }

    @Test(testName = "Шаг 2-Получение пользователем инфо о другом пользователе +допинфо  ")
    public void userInfoAboutOtherUser() {
        String apiKeyUserOne = "f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo = "5f53e117604928097361205d1bba409b5c6211a4";
        String apiKey = "5aed704a56f9c2711d4cf2035a2d28a698b0cca1";
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String password = String.valueOf(new Random().nextInt(500000) + 100000);
        String body = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "mail":"%s",
                 "password":"%s"\s
                 }
                }""", login, mail, password);

    }
}
