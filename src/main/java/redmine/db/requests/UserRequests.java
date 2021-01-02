package redmine.db.requests;

import redmine.api.implementations.RestApiClient;
import redmine.api.implementations.RestRequest;
import redmine.api.interfaces.ApiClient;
import redmine.api.interfaces.HttpMethods;
import redmine.api.interfaces.Request;
import redmine.api.interfaces.Response;
import redmine.managers.Manager;
import redmine.model.user.Language;
import redmine.model.user.Status;
import redmine.model.user.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static redmine.utils.StringGenerators.randomEmail;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class UserRequests {

    /**
     * Получение всех пользователей без части данных
     * @return sql ответ с данными
     */
    public static List<User> getAllUsers() {
        String query = "select * from users";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query);
        return result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin((String) map.get("login"));
                    user.setHashed_password((String) map.get("hashed_password"));
                    user.setFirstname((String) map.get("firstname"));
                    user.setLastname((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus(Status.valueOf(((String) map.get("status")).toUpperCase()));
                    user.setLanguage(Language.valueOf(((String) map.get("language")).toUpperCase()));
                    return user;
                }).collect(Collectors.toList());
    }

    public static User createUser(User user) {
        //TODO Запрос к бд
        //TODO установка id значением сгенерированным в БД
        String login = randomEnglishLowerString(8);
        String firstName = randomEnglishLowerString(12);
        String lastName = randomEnglishLowerString(12);
        String mail = randomEmail();
        String body = String.format("{\n" +
                " \"user\":{\n" +
                " \"login\":\"%s\",\n" +
                " \"firstname\":\"%s\",\n" +
                " \"lastname\":\"%s\",\n" +
                " \"mail\":\"%s\",\n" +
                " \"password\":\"%s\" \n" +
                " }\n" +
                "}", login, firstName, lastName, mail, user.getApiKey());
        ApiClient apiClient = new RestApiClient(user);
        Request request = new RestRequest("users.json", HttpMethods.POST, null, null, body);
        Response response = apiClient.executeRequest(request);
        return user;

    }
}
