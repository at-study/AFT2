package redmine.model.role;

import lombok.*;
import redmine.db.requests.RoleRequests;
import redmine.model.Generatable;
import redmine.utils.StringGenerators;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Role implements Generatable<Role> {
    private Integer id;
    private String name="Auto" + StringGenerators.randomEnglishLowerString(8);
    private Integer position=1;
    private Boolean assignable=true;
    private Integer builtin=0;
    private RolePermissions permissions=new RolePermissions(new HashSet<>());
    private IssuesVisibility issuesVisibility=IssuesVisibility.DEFAULT;
    private UsersVisibility usersVisibility=UsersVisibility.ALL;
    private TimeEntriesVisibility timeEntriesVisibility=TimeEntriesVisibility.ALL;
    private Boolean allRolesManaged=true;
    private String settings = """
            --- !ruby/hash:ActiveSupport::HashWithIndifferentAccess
            permissions_all_trackers: !ruby/hash:ActiveSupport::HashWithIndifferentAccess
              view_issues: '1'
              add_issues: '1'
              edit_issues: '1'
              add_issue_notes: '1'
              delete_issues: '1'
            permissions_tracker_ids: !ruby/hash:ActiveSupport::HashWithIndifferentAccess
              view_issues: []
              add_issues: []
              edit_issues: []
              add_issue_notes: []
              delete_issues: []
            """;

    @Override
    public Role read() {
        return RoleRequests.getRole(this);
    }

    @Override
    public Role update() {
        return RoleRequests.updateRole(this);
    }

    @Override
    public Role create() {
        return RoleRequests.addRole(this);
    }
}
