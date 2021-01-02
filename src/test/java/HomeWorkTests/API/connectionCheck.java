package HomeWorkTests.API;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class connectionCheck {
    @Test(testName = "Запрос проверки на соеденение users-code 200")
    public void restRequestTest() {
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .request(Method.GET, "users.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }
}