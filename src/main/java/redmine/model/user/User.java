package redmine.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)

public class User implements Generatable<User> {
    private Integer id;
    private String login = "Evg" + randomEnglishLowerString(6);
    private String hashedPassword = getHashedPassword();
    private String firstName = "Evg" + randomEnglishLowerString(9);
    private String lastName = "TTT" + randomEnglishLowerString(9);
    private Boolean admin = true;
    private Integer status = 1;
    //TODO last_login_on
    private Language language = Language.RU;
    //TODO updated_on
    //TODO created_on
    private Type type = Type.USER;
    private MailNotification mailNotification = MailNotification.ALL;
    private Boolean inheritMembers;
    private String salt = StringGenerators.randomString(32, "0123456789abcdef");
    private Boolean mustChangePassword = false;
    //TODO passwd_changed_on;
    // private String apiKey=getApiKey();

    @Override
    public User read() {
        return UserRequests.getUser(this);
    }

    @Override
    public User update() {
        return UserRequests.updateUser(this);
    }

    @Override
    public User create() {
        return UserRequests.createUser(this);
    }

    public static String getHashedPassword() {
        String salt = StringGenerators.randomString(32, "0123456789abcdef");
        String password = StringGenerators.randomEnglishString(10);
        return sha1Hex(salt + sha1Hex(password));
    }

}
