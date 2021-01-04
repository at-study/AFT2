package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.util.List;
import java.util.Map;

public class UserRequests {
    public static User createUser(User user) {
        //TODO Запрос к бд
        //TODO установка id значением сгенерированным в БД
        return user;

    }
    public static User updateUser(User user) {
        String query = """
                update public.users
                set hashed_password=?,firstname=?,lastname=?,admin=?,status=?,language=?,
                where login=? RETURNING id;
                """;
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getHashed_password(), user.getFirstname(), user.getLastname(), user.getAdmin().toString(),
                user.getStatus().toString(),user.getLanguage().toString(),user.getLogin());
        user.setId((Integer) result.get(0).get("id"));
        return user;
    }
}
