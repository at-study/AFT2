package redmine.db.requests;

import io.qameta.allure.Step;
import redmine.managers.Manager;
import redmine.model.role.IssuesVisibility;
import redmine.model.role.Role;
import redmine.model.role.RolePermissions;
import redmine.model.role.TimeEntriesVisibility;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoleRequests {

    public static List<Role> getAllRoles() {
        String query = "select * from roles";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query);
        return result.stream()
                .map(map -> {
                    Role role = new Role();
                    role.setId((Integer) map.get("id"));
                    role.setPosition((Integer) map.get("position"));
                    role.setBuiltin((Integer) map.get("builtin"));
                    role.setName((String) map.get("name"));
                    role.setAssignable((Boolean) map.get("assignable"));
                    role.setIssuesVisibility(IssuesVisibility.valueOf(((String) map.get("issues_visibility")).toUpperCase()));
                    role.setPermissions(RolePermissions.of((String) map.get("permissions")));
                    role.setTimeEntriesVisibility(TimeEntriesVisibility.valueOf(((String) map.get("time_entries_visibility")).toUpperCase()));
                    role.setAllRolesManaged((Boolean) map.get("all_roles_managed"));
                    role.setSettings((String) map.get("settings"));
                    return role;
                }).collect(Collectors.toList());
    }

    @Step("Информация о роли получена")
    public static Role getRole(Role objectRole) {
        return getAllRoles().stream()
                .filter(role -> {
                    if (objectRole.getId() == null) {
                        return objectRole.getName().equals(role.getName());
                    } else return (objectRole.getId().equals(role.getId()));
                })
                .findFirst()
                .orElse(null);
    }

    @Step("Создание роли")
    public static Role addRole(Role role) {
        String query = "insert into public.roles\n" +
                "(id,\"name\",\"position\",assignable,builtin,\"permissions\",issues_visibility,users_visibility,time_entries_visibility,all_roles_managed,settings)values(DEFAULT,?,?,?,?,?,?,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                role.getName(), role.getPosition(), role.getAssignable(), role.getBuiltin(), role.getPermissions().toString(),
                role.getIssuesVisibility().toString(), role.getUsersVisibility().toString(), role.getTimeEntriesVisibility().toString(),
                role.getAllRolesManaged(), role.getSettings());
        role.setId((Integer) result.get(0).get("id"));
        return role;
    }

    @Step("Информация о роли измененна")
    public static Role updateRole(Role role) {
        String query = "update public.roles\n" +
                "set position=?,assignable=?,builtin=?,permissions=?,issues_visibility=?,users_visibility=?,time_entries_visibility=?,all_roles_managed=?,settings=?\n" +
                "where name=? RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                role.getPosition(), role.getAssignable(), role.getBuiltin(), role.getPermissions().toString(),
                role.getIssuesVisibility().toString(), role.getUsersVisibility().toString(), role.getTimeEntriesVisibility().toString(),
                role.getAllRolesManaged(), role.getSettings(), role.getName());
        role.setId((Integer) result.get(0).get("id"));
        return role;
    }
}

