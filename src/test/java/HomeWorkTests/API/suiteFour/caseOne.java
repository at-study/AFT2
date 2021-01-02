package HomeWorkTests.API.suiteFour;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.model.user.User;

import static io.restassured.RestAssured.given;

/**
 *  user -evgenyttuser ;
 *  api=5aed704a56f9c2711d4cf2035a2d28a698b0cca1
 *   user -evgenyttuser2 ;
 *  *  api=5f53e117604928097361205d1bba409b5c6211a4
 */
public class caseOne {
    User user;

    @BeforeMethod
    public void prepareFixtures() {
        user = new User().generate();
    }
    @Test(testName = "Тест соеденения с таблицей users", priority = 1)
    public void connectionCheck() {
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getHeaders().containsKey("Content-Type"));
    }
    @Test(testName = "Создание пользователя-первый кейс",priority=10,
            description = "Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    public void userCreationWithStatusTwo(){

    }
}
