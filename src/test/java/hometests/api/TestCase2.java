package hometests.api;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class TestCase2 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setAdmin(false).setStatus(1).generate();
    }

    @Test(testName = "Шаг 1-Отправить запрос POST на создание пользователя НЕ АДМИНИСТРАТОРОМ-403 ")
    public void userCreationByNonAdmin() {
        String apiKey = user.getApiKey();
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = String.valueOf(new Random().nextInt(50000000) + 10000000);
        String body = String.format("""
                {
                 "user":{
                 "login":"%s",
                 "mail":"%s",
                 "firstname":"%s",
                 "lastname":"%s",
                 "password":"%s"\s
                 }
                }""", login, mail, name, lastName, password);
        Response response = given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .header("X-Redmine-API-Key", apiKey)
                .body(body)
                .request(Method.POST, "users.json");
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
