package redmine.model.Dto;

import lombok.Data;

import java.util.List;

@Data
public class UserCreationError {
    List<String> errors;
}
