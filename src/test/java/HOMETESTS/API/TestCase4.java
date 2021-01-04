package HOMETESTS.API;
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
import redmine.db.requests.RoleRequests;
import redmine.db.requests.UserRequests;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;


public class TestCase4 {

    @Test(testName = "Шаг 1-Удаление пользователя другим пользователем и проверка в бд ")
    public void userDeleteByOtherUser(){
        String apiKeyUserOne="f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo="5f53e117604928097361205d1bba409b5c6211a4";

    }
    @Test(testName = "Шаг 2-Удаление пользователя самим собою и проверка в бд ")
    public void userDeleteByHimself(){
        String apiKeyUserOne="f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo="5f53e117604928097361205d1bba409b5c6211a4";

    }
}
