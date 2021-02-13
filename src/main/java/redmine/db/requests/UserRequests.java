package redmine.db.requests;

import io.qameta.allure.Step;
import org.testng.Assert;
import redmine.managers.Manager;
import redmine.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static redmine.utils.StringGenerators.randomEmail;


public class UserRequests {
    @Step("Информация о пользователях получена")
    public static List<User> getAllUsers() {
        String query = "select * from users u inner join tokens t on u.id=t.user_id inner join email_addresses e on u.id=e.user_id";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin((String) map.get("login"));
                    user.setHashedPassword((String) map.get("hashed_password"));
                    user.setFirstName((String) map.get("firstname"));
                    user.setLastName((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus(((Integer) map.get("status")));
                    return user;
                }).collect(Collectors.toList());
    }

    @Step("Создание пользователя")
    public static User createUser(User user) {
        String query = "insert into public.users\n" +
                "(id,login,hashed_password,firstname,lastname,\"admin\",\"status\",\"language\",mail_notification,type,salt)values(DEFAULT,?,?,?,?,?,?,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLanguage().toString().toLowerCase(),
                user.getMailNotification().toString().toLowerCase(),
                user.getType(),
                user.getSalt());
        user.setId((Integer) result.get(0).get("id"));
        Integer userId = (Integer) result.get(0).get("id");

        String value = user.getApiKey();
        String action = "api";
        String queryForToken = "insert into public.tokens\n" +
                "(id,user_id,action,value,created_on,updated_on)values(DEFAULT,?,?,?,?,?) RETURNING id;\n";
        Manager.dbConnection.executePreparedQuery(queryForToken, userId, action, value, LocalDateTime.now(), LocalDateTime.now());

        String queryForEmail = "insert into public.email_addresses\n" +
                "(id,user_id,address,is_default,notify,created_on,updated_on)values(DEFAULT,?,?,?,?,?,?) RETURNING id;\n";
        Manager.dbConnection.executePreparedQuery(queryForEmail, userId, randomEmail(), true, true, LocalDateTime.now(), LocalDateTime.now());
        return user;
    }

    @Step("Информация о пользователе изменена")
    public static User updateUser(User user) {
        String query = "update public.users\n" +
                "set login=?,hashed_password=?,firstname=?,lastname=?,admin=?,status=?,language=?,\n" +
                "where name=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(), user.getHashedPassword(), user.getFirstName(), user.getLastName(), user.getAdmin().toString(),
                user.getStatus().toString(), user.getLanguage().toString());
        user.setId((Integer) result.get(0).get("id"));
        return user;
    }


    @Step("Информация о пользователе по ид получена")
    public static List<User> getUserById(Integer id) {
        String query = String.format("select * from users u inner join tokens t on u.id=t.user_id inner join email_addresses e on u.id=e.user_id where id='%s'", id);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        return result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin((String) map.get("login"));
                    user.setHashedPassword((String) map.get("hashed_password"));
                    user.setFirstName((String) map.get("firstname"));
                    user.setLastName((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus(((Integer) map.get("status")));
                    return user;
                }).collect(Collectors.toList());
    }

    @Step("Информация о пользователе по ид получена")
    public static List<User> getUserByLogin(String login) {
        String query = String.format("select * from users u inner join tokens t on u.id=t.user_id inner join email_addresses e on u.id=e.user_id where login='%s'", login);
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        Assert.assertEquals(result.size(), 1, "Проверка размера результата");
        return result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin((String) map.get("login"));
                    user.setHashedPassword((String) map.get("hashed_password"));
                    user.setFirstName((String) map.get("firstname"));
                    user.setLastName((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus(((Integer) map.get("status")));
                    return user;
                }).collect(Collectors.toList());
    }

    @Step("Информация о пользователе получена")
    public static User getUser(User objectUser) {
        return getAllUsers().stream()
                .filter(user -> {
                    if (objectUser.getId() == null) {
                        return objectUser.getLogin().equals(user.getLogin());
                    } else return (objectUser.getId().equals(user.getId()));
                })
                .findFirst()
                .orElse(null);
    }
}
