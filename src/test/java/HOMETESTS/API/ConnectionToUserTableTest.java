package HOMETESTS.API;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ConnectionToUserTableTest {

    String apiKey = "f02b2da01a468c4116be898911481d1b928c15f9";

    @Test(testName = "Проверка соеденения с User-статус 200")
    public void restRequestTest() {
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .request(Method.GET, "roles.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }
}
