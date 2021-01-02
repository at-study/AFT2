package HomeWorkTests.API.suiteFour;
import org.testng.annotations.Test;
/**
 *  user -evgenyttuser ;
 *  api=5aed704a56f9c2711d4cf2035a2d28a698b0cca1
 *   user -evgenyttuser2 ;
 *  *  api=5f53e117604928097361205d1bba409b5c6211a4
 */
public class caseOne {

    @Test(testName = "Создание пользователя-первый кейс",priority=10,
            description = "Отправить запрос POST на создание пользователя (данные пользователя должны быть сгенерированы корректно, пользователь должен иметь status = 2)")
    public void userCreationWithStatusTwo(){
    }
}
