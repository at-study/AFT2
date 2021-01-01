package redmine.model.user;

import lombok.*;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class User implements Generatable<User> {
    private Integer id;
    private String login;
    private String hashed_password;
    private String firstname;
    private String lastname;
    private Boolean admin;
    private Status status;
    //TODO last_login_on
    private Language language=Language.RU;
    //TODO updated_on
    //TODO created_on
    private Type type;
    private MailNotification mail_notification;
    private Boolean inherit_members;
    private String salt;
    private Boolean must_change_passwd;
    //TODO passwd_changed_on;

    @Override
    public User read() {
        return null;
    }

    @Override
    public User update() {
        return null;
    }

    @Override
    public User create() {
        return UserRequests.createUser(this);
    }

    /**
     * custom user for restapi client generateRandomString(40,"0..f")
     * "f02b2da01a468c4116be898911481d1b928c15f9";
     */
    public String getApiKey() {
       String salt= StringGenerators.randomString(32,"0123456789abcdef");
       String password=StringGenerators.randomEnglishLowerString(10);
       String hashed_password=sha1Hex(salt+sha1Hex(password));
        return hashed_password;
    }

}
