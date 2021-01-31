package redmine.db.requests;

import io.qameta.allure.Step;
import redmine.model.project.Project;
import redmine.model.role.Role;
import redmine.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static redmine.managers.Manager.*;

public class ProjectRequests {
    @Step("Получение всех проектов из ДБ")
    public static List<Project> getAllProjects() {
        String query = "select * from projects";
        List<Map<String, Object>> result = dbConnection.executeQuery(query);
        return result.stream()
                .map(map -> {
                    Project project = new Project();
                    project.setId((Integer) map.get("id"));
                    project.setName((String) map.get("name"));
                    project.setDescription((String) map.get("description"));
                    project.setHomepage((String) map.get("homepage"));
                    project.setIsPublic((Boolean) map.get("is_public"));
                    project.setParentId((Integer) map.get("parent_id"));
                    project.setIdentifier((String) map.get("identifier"));
                    return project;
                }).collect(Collectors.toList());
    }

    @Step("Создание проекта")
    public static Project createProject(Project project) {
        String query = "insert into public.projects\n" +
                "(id,\"name\",\"description\",\"is_public\",status,identifier,lft,rgt,created_on,updated_on)values(DEFAULT,?,?,?,?,?,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> result = dbConnection.executePreparedQuery(query,
                project.getName(),
                project.getDescription(),
                project.getIsPublic(),
                project.getStatus(),
                project.getIdentifier(),
                project.getLft(),
                project.getRgt(),
                project.getCreatedOn(),
                project.getUpdatedOn());
        project.setId((Integer) result.get(0).get("id"));
        return project;
    }

    @Step("Добавление пользователя и роли к проекту")
    public static Project addUserAndRoleToProject(Project project, User user, Role role) {
        String queryPutIntoMembers = "insert into public.members\n" +
                "(id,user_id,project_id,created_on,mail_notification) values(default,?,?,?,false) RETURNING id;\n";
        List<Map<String, Object>> resultQuaryPutIntoMembers = dbConnection.executePreparedQuery(queryPutIntoMembers,
                user.getId(), project.getId(), LocalDateTime.now());
        user.setId((Integer) resultQuaryPutIntoMembers.get(0).get("id"));
        Integer membersId = (Integer) resultQuaryPutIntoMembers.get(0).get("id");

        String queryPutToMembersRoles = "insert into public.member_roles\n" +
                "(id,member_id,role_id,inherited_from) values (default,?,?,NULL) returning id;\n";
        dbConnection.executePreparedQuery(queryPutToMembersRoles,
                membersId, role.getId());
        return project;
    }
}

