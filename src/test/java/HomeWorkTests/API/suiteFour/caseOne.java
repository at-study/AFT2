package HomeWorkTests.API.suiteFour;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 *  user -evgenyttuser ;
 *  api=5aed704a56f9c2711d4cf2035a2d28a698b0cca1
 *   user -evgenyttuser2 ;
 *  *  api=5f53e117604928097361205d1bba409b5c6211a4
 */
public class caseOne {
    @BeforeMethod
    public void connectionCheck() {
        given().baseUri("http://edu-at.dfu.i-teco.ru/")
                .contentType(ContentType.JSON)
                .request(Method.GET, "users.json")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
        System.out.println("Проверка соеденения");
    }
    @Test(testName = "Создание пользователя-первый кейс",priority=10,
            description = "Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    public void userCreationWithStatusTwo(){

    }
}
