package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRequests {

    public static List<User> getAllUsers() {
        String query = "select * from users u inner join tokens t on u.id=t.user_id inner join email_addresses e on u.id=e.user_id";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    User user = new User();
                    user.setId((Integer) map.get("id"));
                    user.setLogin((String) map.get("login"));
                    user.setHashed_password((String) map.get("hashed_password"));
                    user.setFirstname((String) map.get("firstname"));
                    user.setLastname((String) map.get("lastname"));
                    user.setAdmin((Boolean) map.get("admin"));
                    user.setStatus(((Integer) map.get("status")));
                    return user;
                }).collect(Collectors.toList());

    }


    public static User createUser(User user) {
        //TODO Запрос к бд
        //TODO установка id значением сгенерированным в БД
        return user;

    }

    public static User updateUser(User user) {
        String query = """
                update public.users
                set login=?,firstname=?,lastname=?,admin=?,status=?,language=?,
                where hashed_password=? RETURNING id;
                """;
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(), user.getFirstname(), user.getLastname(), user.getAdmin().toString(),
                user.getStatus().toString(),user.getLanguage().toString(),user.getHashed_password());
        user.setId((Integer) result.get(0).get("id"));
        return user;
    }
}
