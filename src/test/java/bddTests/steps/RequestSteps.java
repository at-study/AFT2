package bddTests.steps;

import cucumber.api.java.ru.Если;
import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Context;
import redmine.model.dto.UserDto;
import redmine.model.dto.UserInfo;
import redmine.model.user.User;

import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;
import static redmine.utils.gson.GsonHelper.getGson;

public class RequestSteps {

    @Если("Отправить запрос на создание пользователя через API пользователем {string}")
    public void answerOnUserCreationRequest(String stashId) {
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        String login = randomEnglishLowerString(8);
        String mail = randomEmail();
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);
        Integer status = 1;
        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                .setLastname(lastName).setMail(mail).setStatus(status).setPassword(password).setAdmin(false));
        String body = getGson().toJson(userDto);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Context.put("user_operation_response", response);
    }

    @Если("Отправить запрос на получение информации пользователем {string} о самом себе")
    public void answerOnGetRequestAboutHimselfByNonAdmin(String stashId) {
        User firstUser = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(firstUser);
        String uri = String.format("users/%d.json", firstUser.getId());
        Request request = new RestRequest(uri, HttpMethods.GET, null, null, null);
        Response response = apiClient.executeRequest(request);
        Context.put("user_operation_response", response);
    }

     @Если("Отправить запрос на удаление пользователем {string} самого себя")
     public void deleteRequestByHimself(String stashId){
         User firstUser = Context.get(stashId, User.class);
         Integer userId = firstUser.getId();
         String userApiKey = firstUser.getApiKey();
         ApiClient apiClient = new RestApiClient(firstUser);
         String uri = String.format("users/%d.json", firstUser.getId());
         Request request = new RestRequest(uri, HttpMethods.DELETE, null, null, null);
         Response response = apiClient.executeRequest(request);
         Context.put("user_operation_response", response);
     }



}