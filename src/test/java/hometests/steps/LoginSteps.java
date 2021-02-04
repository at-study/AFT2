package hometests.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import redmine.managers.Context;
import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.ui.pages.LoginPage;

import static redmine.ui.pages.Pages.getPage;

public class LoginSteps {
    @Если("Авторизоваться пользователем {string}")
    public void authorizeBy(String userStashId){
        User user= Context.getStash().get(userStashId,User.class);
        getPage(LoginPage.class).login(user.getLogin(),user.getPassword());
    }

    @И("Открыт браузер на главной странице")
    public void openBrowserOnMainPage(){
        Manager.openPage("login");
    }
}
