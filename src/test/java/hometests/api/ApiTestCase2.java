package hometests.api;

import io.qameta.allure.Description;
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
import static org.testng.Assert.assertEquals;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class ApiTestCase2 {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().setAdmin(false).setStatus(1).generate();
    }

    @Test(testName = "Шаг 1-Отправить запрос POST на создание пользователя НЕ АДМИНИСТРАТОРОМ-403 ",description = "3. Получение пользователей. Пользователь без прав администратора")
    @Description("1. Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно)")
    public void userCreationByNonAdmin() {
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = String.valueOf(new Random().nextInt(50000000) + 10000000);
        String body = String.format("{\n" +
                "                 \"user\":{\n" +
                "                 \"login\":\"%s\",\n" +
                "                 \"mail\":\"%s\",\n" +
                "                 \"firstname\":\"%s\",\n" +
                "                 \"lastname\":\"%s\",\n" +
                "                 \"password\":\"%s\"\n" +
                "                 }\n" +
                "                }", login, mail, name, lastName, password);
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        apiClient.executeRequest(request);
        redmine.api.interfaces.Response userCreationByNonAdmin = apiClient.executeRequest(request);
        assertEquals(userCreationByNonAdmin.getStatusCode(), 403);
    }
}
