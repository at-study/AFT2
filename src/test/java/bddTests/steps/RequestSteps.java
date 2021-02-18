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

    @Если("Отправить запрос на создание пользователя {string} {string} со статусом:{int}")
    public void answerOnUserCreationRequest(String stashId,String userType,int status) {
        User user = Context.get(stashId, User.class);
        ApiClient apiClient = new RestApiClient(user);
        if (userType.equals("пользователем")){
        String login = randomEnglishLowerString(8);
        String name = randomEnglishLowerString(8);
        String lastName = randomEnglishLowerString(8);
        String password = randomEnglishLowerString(8);
        UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                .setLastname(lastName).setMail(randomEmail()).setStatus(status).setPassword(password).setAdmin(false));
        String body = getGson().toJson(userDto);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        Context.put("response",response);}
        else{
            String login = randomEnglishLowerString(8);
            String name = randomEnglishLowerString(8);
            String lastName = randomEnglishLowerString(8);
            String password = randomEnglishLowerString(8);
            UserDto userDto = new UserDto().setUser(new UserInfo().setLogin(login).setFirstname(name)
                    .setLastname(lastName).setMail(randomEmail()).setStatus(status).setPassword(password).setAdmin(true));
            String body = getGson().toJson(userDto);
            Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
            Response response = apiClient.executeRequest(request);
            Context.put("response",response);}


    }

}
