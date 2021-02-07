package hometests.steps;

import cucumber.api.java.ru.Если;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.model.user.User;

public  class RequestSteps {
    @Если("Отправить запрос на создание пользователя {string} с параметрами:")
    public static Response answerOnUserCreationRequest(String userStashId){
        User admin = Context.get(userStashId, User.class);
        String apiKeyForQuery=admin.getApiKey();
        System.out.println(apiKeyForQuery);
        return null;
    }
}
