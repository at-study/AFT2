package redmine.db.requests;

import redmine.managers.Manager;
import redmine.model.project.Project;
import redmine.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static redmine.utils.StringGenerators.randomEnglishLowerString;

public class ProjectRequests {
    public static List<Project> getAllProjects() {
        String query = "select * from projects";
        List<Map<String, Object>> result = Manager.dbConnection.executeQuery(query);
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

    public static Project createProject(Project project) {
        String query = "insert into public.projects\n" +
                "(id,\"name\",\"description\",\"is_public\",status)values(DEFAULT,?,?,?,?) RETURNING id;\n";
        List<Map<String, Object>> result = Manager.dbConnection.executePreparedQuery(query,
                project.getName(),
                project.getDescription(),
                project.getIsPublic(),
                project.getStatus());
        project.setId((Integer) result.get(0).get("id"));
        Integer projectId = (Integer) result.get(0).get("id");
        return project;
    }
}

