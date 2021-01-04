package HOMETESTS.API;
import org.testng.annotations.Test;

public class TestCase4 {

    @Test(testName = "Шаг 1-Удаление пользователя другим пользователем и проверка в бд ")
    public void userDeleteByOtherUser() {
        String apiKeyUserOne = "f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo = "5f53e117604928097361205d1bba409b5c6211a4";

    }

    @Test(testName = "Шаг 2-Удаление пользователя самим собою и проверка в бд ")
    public void userDeleteByHimself() {
        String apiKeyUserOne = "f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo = "5f53e117604928097361205d1bba409b5c6211a4";

    }
}
