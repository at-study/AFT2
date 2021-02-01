package redmine.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserCreationError {
    List<String> errors;
}
