package RestTests;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static redmine.utils.StringGenerators.*;

public class MyFirstRestTest {
    @Test(testName = "Первый запрос апи на получение 200")
    public void restRequestTest(){
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .request(Method.GET,"roles.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test(testName = "Формирование тела запроса и создание пользователя")
    public void createUserTest() {
        String apiKey = "f02b2da01a468c4116be898911481d1b928c15f9";
        String login = randomEnglishLowerString(8);
        String firstname = randomEnglishLowerString(12);
        String lastname = randomEnglishLowerString(12);
        String mail = randomEmail();
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"1qaz@WSX\",\n" +
                " }\n" +
                "}", login, firstname, lastname, mail);

        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .body(body)
                .request(Method.POST, "roles.json");

        Assert.assertEquals(response.getStatusCode(), 201);
        String responseBody=response.getBody().asString();
    }
}