package redmine.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectInfo {
    private Integer id;
    private String name;
    private String description;
    private String homepage;
    private Boolean is_public;
    private Integer parent_id;
    private LocalDateTime created_on;
    private LocalDateTime updated_on;
    private String identifier;
    private Integer status;
    private Integer lft;
    private Integer rgt;
    private Boolean inherit_members;
    private Integer default_version_id;
    private Integer default_assigned_to_id;
}

