package RestTests;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

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
    public void createUserTest(){
        String apiKey="f02b2da01a468c4116be898911481d1b928c15f9";
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key",apiKey)
                .body(body)