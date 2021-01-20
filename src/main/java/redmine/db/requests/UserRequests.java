package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.user.User;
import redmine.utils.StringGenerators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class UserRequests {

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


    public static User createUser(User user) {
        String query = "insert into public.users\n" +
                "(id,login,hashed_password,firstname,lastname,admin,status,language,mail_notification,type,salt)values(DEFAULT,?,?,?,?,?,?,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAdmin(),
                user.getStatus(),
                user.getLanguage().toString().toLowerCase(),
                user.getMailNotification().toString().toLowerCase(),
                user.getType().toString(),
                user.getSalt());
        user.setId((Integer) result.get(0).get("id"));
        Integer userId= (Integer) result.get(0).get("id");
        System.out.println("User created,id :"+userId);

        String value= StringGenerators.randomString(40,"0123456789abcdef");
        String action="api";
        String queryForToken = "insert into public.tokens\n" +
                "(id,user_id,action,value,created_on)values(DEFAULT,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> resultForToken = Manager.dbConnection.executePreparedQuery(queryForToken,userId,action,value, LocalDate.now());
        user.setId((Integer) resultForToken.get(0).get("id"));

        String email=randomEnglishLowerString(8)+"@"+randomEnglishLowerString(9)+"."+randomEnglishLowerString(3);
        String queryForEmail = "insert into public.email_addresses\n" +
                "(id,user_id,address,is_default,notify,created_on,updated_on)values(DEFAULT,?,?,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> resultForEmail = Manager.dbConnection.executePreparedQuery(queryForEmail,userId,email,true,true, LocalDate.now(),LocalDate.now());
        user.setId((Integer) resultForEmail.get(0).get("id"));

        return user;
    }

    public static User updateUser(User user) {
        String query = "update public.users\n" +
                       "set login=?,firstname=?,lastname=?,admin=?,status=?,language=?,\n" +
                       "where hashed_password=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                user.getLogin(), user.getFirstName(), user.getLastName(), user.getAdmin().toString(),
                user.getStatus().toString(),user.getLanguage().toString(),user.getHashedPassword());
        user.setId((Integer) result.get(0).get("id"));

        return user;
    }

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
