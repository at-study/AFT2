package redmine.model.project;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class Project {
    private Integer id;
    private String name;
    private String description;
    private Boolean isPublic;
    private String identifier;
    //TODO updated_on
    //TODO created_on
    private Status status;
    private Integer ift;
    private Integer rgt;
    private Boolean inheritMembers;
}
