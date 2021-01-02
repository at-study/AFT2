package HomeWorkTests.API.Helpers;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class connectionCheck {
    String apiKey = "f2b07eec53f92b54a8522488ca25491167419076";

    @Test(testName = "Запрос проверки на соеденение users-code 200")
    public void restRequestUsersConnectionTest() {
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .request(Method.GET, "users.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }
}