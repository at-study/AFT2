package redmine.model.Dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserInfo {
    private Integer id;
    private String login;
    private Boolean admin;
    private String firstName;
    private String lastName;
    private String mail;
    private LocalDateTime created_on;
    private LocalDateTime last_login_on;
    private String api_key;
    private Integer status;
}
