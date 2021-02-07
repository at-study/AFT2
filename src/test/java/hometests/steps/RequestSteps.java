package hometests.steps;

import cucumber.api.java.ru.Если;
import redmine.api.interfaces.Response;

public class RequestSteps {
    @Если("Отправить запрос на создание пользователя {string} с параметрами:")
    public Response answerOnUserCreationRequest(){
        return null;
    }
}
