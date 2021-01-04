package HOMETESTS.API;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase2 {
    @Test(testName = "Шаг 1-Отправить запрос POST на создание пользователя НЕ АДМИНИСТРАТОРОМ-403 ")
    public void userCreationByNonAdmin() {
        String apiKey = "5aed704a56f9c2711d4cf2035a2d28a698b0cca1";
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String password = String.valueOf(new Random().nextInt(500000) + 100000);
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, password);

        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .body(body)
                .request(Method.POST, "users.json");
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
