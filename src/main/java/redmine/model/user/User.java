package redmine.model.user;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.requests.UserRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;
import java.time.LocalDateTime;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Accessors(chain = true)
@Data
public class User implements Generatable<User> {
    private Integer id;
    private String login = "Evg" + randomEnglishLowerString(6);
    private String password = StringGenerators.randomEnglishString(10);
    private String salt = StringGenerators.randomString(32, "0123456789abcdef");
    private String hashedPassword = getGeneratedHashedPassword();
    private String firstName = "Evg" + randomEnglishLowerString(9);
    private String lastName = "TTT" + randomEnglishLowerString(9);
    private Boolean admin = true;
    private Integer status = 1;
    private LocalDateTime loginOn = LocalDateTime.now();
    private Language language = Language.RU;
    private String type = "User";
    private MailNotification mailNotification = MailNotification.ALL;
    private Boolean inheritMembers;
    private Boolean mustChangePassword = false;
    private LocalDateTime changedOn= LocalDateTime.now();
    private String apiKey = StringGenerators.randomString(40, "0123456789abcdef");

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

    public String getGeneratedHashedPassword() {
        return sha1Hex(salt + sha1Hex(password));
    }


}
