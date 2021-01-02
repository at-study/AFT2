package HomeWorkTests.API;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ConnectionCheck {
    String apiKey="f2b07eec53f92b54a8522488ca25491167419076";
    @Test(testName = "Проверка соеденения юзерс")
    public void connectionCheck() {
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .request(Method.GET, "users.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
        System.out.println("Проверка соеденения");
    }
}
