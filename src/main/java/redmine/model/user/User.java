package redmine.model.user;

import lombok.*;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class User implements Generatable<User> {
    private Integer id;
    private String login="Evg"+randomEnglishLowerString(5);
    private String hashedPassword;
    private String firstName="Evg"+randomEnglishLowerString(9);
    private String lastName="TTT"+randomEnglishLowerString(9);
    private Boolean admin;
    private Integer status;
    //TODO last_login_on
    private Language language=Language.RU;
    //TODO updated_on
    //TODO created_on
    private Type type=Type.USER;
    private MailNotification mailNotification=MailNotification.ALL;
    private Boolean inherit_members;
    private String salt;
    private Boolean mustChangePassword =false;
    //TODO passwd_changed_on;

    @Override
    public User read() {
        return null;
    }

    @Override
    public User update() {
        return UserRequests.updateUser(this);
    }

    @Override
    public User create() {
        return UserRequests.createUser(this);
    }

    /**
     * @return Admin apiKey="f02b2da01a468c4116be898911481d1b928c15f9"
     */
    public static String getApiKey() {
        String salt=StringGenerators.randomString(32,"0123456789abcdef");
        String password=StringGenerators.randomEnglishString(10);
        String hashedPassword=sha1Hex(salt+sha1Hex(password));
        return "f02b2da01a468c4116be898911481d1b928c15f9";
    }

}
