package HOMETESTS.API;

import org.testng.annotations.Test;

public class TestCase3 {
    @Test(testName = "Шаг 1-Получение пользователем инфо о самом себе+допинфо ")
    public void userInfoAboutHimself() {
        String apiKeyUserOne = "f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo = "5f53e117604928097361205d1bba409b5c6211a4";
    }

    @Test(testName = "Шаг 2-Получение пользователем инфо о другом пользователе +допинфо  ")
    public void userInfoAboutOtherUser() {
        String apiKeyUserOne = "f02b2da01a468c4116be898911481d1b928c15f9";
        String apiKeyUserTwo = "5f53e117604928097361205d1bba409b5c6211a4";
    }
}
