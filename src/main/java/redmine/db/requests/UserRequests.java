package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.user.Language;
import redmine.model.user.Status;
import redmine.model.user.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String query = """
                insert into public.users
                (id,login,hashed_password,firstname,lastname,admin,status,last_login_on,language,updated_on,created_on,
                type,mail_notification,inherit_members,salt,must_change_passwd,passwd_changed_on;)values(DEFAULT,?,?,?,?,?,?,?,?,?,?) RETURNING id;
                """;
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(), user.getHashed_password(),user.getFirstname(),user.getLastname(),user.getAdmin(),user.getStatus(),
        user.getLast_login_on(),user.getLanguage());
        user.setId((Integer) result.get(0).get("id"));
        return user;

    }
}
