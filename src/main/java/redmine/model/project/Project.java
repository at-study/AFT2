package redmine.model.project;

import lombok.*;

import static redmine.utils.StringGenerators.randomEnglishLowerString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode

public class Project {
    private Integer id;
    private String name="EvgProject" + randomEnglishLowerString(6);
    private String description;
    private Boolean isPublic;
    private String identifier="Autoproject"+ randomEnglishLowerString(6);;
    //TODO updated_on
    //TODO created_on
    private Integer status=1;
    private Integer ift;
    private Integer rgt;
    private Boolean inheritMembers=false;
}
