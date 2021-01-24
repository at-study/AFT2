package redmine.model.project;

import lombok.*;
import lombok.experimental.Accessors;
import redmine.db.requests.ProjectRequests;
import redmine.model.Generatable;

import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
@Data

public class Project implements Generatable<Project> {
    private Integer id;
    private String name = "EvgProject" + randomEnglishLowerString(6);
    private String description = "Desc  " + randomEnglishLowerString(15);
    private String homepage;
    private Integer parentId;
    private Boolean isPublic;
    private String identifier = "Autoproject" + randomEnglishLowerString(6);
    //TODO updated_on
    //TODO created_on
    private Integer status = 1;
    private Integer lft = 3;
    private Integer rgt = 3;
    private Boolean inheritMembers = false;

    @Override
    public Project read() {
        return null;
    }

    @Override
    public Project update() {
        return null;
    }

    @Override
    public Project create() {
        return ProjectRequests.createProject(this);
    }
}
