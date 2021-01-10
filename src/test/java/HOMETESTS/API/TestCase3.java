package HOMETESTS.API;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.model.user.User;

import java.util.Random;
import static io.restassured.RestAssured.given;
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
        Request createRequest = new RestRequest("users.json", HttpMethods.POST, null, null, body);
       // Response createResponse = apiClient.executeRequest(createRequest);
      //  Assert.assertEquals(createResponse.getStatusCode(), 201);
        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                //.header("X-Redmine-API-Key", )
                .body(body)
                .request(Method.POST, "users.json");
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test(testName = "Шаг 2-Получение пользователем инфо о другом пользователе +допинфо  ")
    public void userInfoAboutOtherUser() {
        String apiKeyUserOne = "f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo = "5f53e117604928097361205d1bba409b5c6211a4";
        String apiKey = "5aed704a56f9c2711d4cf2035a2d28a698b0cca1";
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String password = String.valueOf(new Random().nextInt(500000) + 100000);
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, mail, password);
        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .body(body)
                .request(Method.POST, "users.json");
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
