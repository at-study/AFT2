package hometests.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;

import static redmine.ui.pages.helpers.Pages.getPage;

public class LoginSteps {
    @Если("Авторизоваться пользователем {string}")
    public void authorizeBy(String userStashId) {
        User user = Context.get(userStashId, User.class);
        getPage(LoginPage.class).login(user.getLogin(), user.getPassword());
    }

    @И("Открыт браузер на главной странице")
    public void openBrowserOnMainPage() {
        Manager.openPage("login");
    }

    @И("Отображается ошибка с текстом {string}")
    public void flashNoticeAboutAccount(String fieldName) {
        WebElement element= CucumberPageObjectHelper.getElementBy("Вход в систему", fieldName);
        Asserts.assertEquals(getPage(LoginPage.class).errorMessage(), "Your account was created and is now pending administrator approval.");
    }
}
