package redmine.model.user;
import lombok.*;
@Getter
@AllArgsConstructor

public enum Status {
    ANONYMOUS(0),
    ACTIVE(1),
    REGISTERED(2),
    LOCKED(3);

    public final Integer status;
}
