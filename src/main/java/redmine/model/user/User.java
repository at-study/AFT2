package redmine.model.user;

import lombok.*;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;

import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class User implements Generatable<User> {
    private Integer id;
    private String login;
    private String hashed_password;
    private String firstname="Evg"+randomEnglishLowerString(9);
    private String lastname="TTT"+randomEnglishLowerString(9);;
    private Boolean admin;
    private Status status;
    //TODO last_login_on
    private Language language=Language.RU;
    //TODO updated_on
    //TODO created_on
    private Type type=Type.USER;
    private MailNotification mail_notification=MailNotification.ALL;
    private Boolean inherit_members;
    private String salt;
    private Boolean must_change_passwd=false;
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
     * custom user for restapi client generateRandomString(40,"0..f");
     */
    public static String getApiKey() {
        //TODO  Изменить на генерацию ключа API
        return "f02b2da01a468c4116be898911481d1b928c15f9";
    }

}
