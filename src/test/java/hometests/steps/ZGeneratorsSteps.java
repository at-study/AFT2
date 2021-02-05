package hometests.steps;

import cucumber.api.java.ru.Пусть;
import redmine.managers.Context;
import redmine.model.role.IssuesVisibility;
import redmine.model.role.Role;
import redmine.model.role.TimeEntriesVisibility;
import redmine.model.role.UsersVisibility;
import redmine.model.user.User;

import java.util.Map;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static org.testng.Assert.assertEquals;

public class ZGeneratorsSteps {
    @Пусть("Существует пользователь {string} с параметрами:")
    public void generateAndSaveUser(String stashId, Map<String, String> params) {
        User user = new User();
        if (params.containsKey("admin")) {
            user.setAdmin(Boolean.parseBoolean(params.get("admin")));
            if (params.containsKey("status")) {
                user.setStatus(Integer.parseInt(params.get("status")));
            }
            user.generate();
            Context.put(stashId, user);
        }
    }

    @Пусть("В системе существует роль {string} с параметрами по умолчанию")
    public void generateDefaultRole(String roleStashId) {
        Role role = new Role().generate();
        Context.put(roleStashId, role);
    }

    @Пусть("В системе существует роль {string} с параметрами:")
        public void generateRoleWithParameters(String roleStashId,Map<String,String> parameters) {
            Role role = new Role();
        if (parameters.containsKey("Позиция")) { role.setPosition(parseInt(parameters.get("Позиция"))); }
        if (parameters.containsKey("Встроенная")) { role.setBuiltin(parseInt(parameters.get("Встроенная"))); }
        if (parameters.containsKey("Задача может быть назначена этой роли")) {
            role.setAssignable((parseBoolean(parameters.get("Задача может быть назначена этой роли")))); }
        if (parameters.containsKey("Видимость задач")) {
            role.setIssuesVisibility(IssuesVisibility.of(parameters.get("Видимость задач"))); }
        if (parameters.containsKey("Видимость пользователей")) {
            role.setUsersVisibility(UsersVisibility.of(parameters.get("Видимость пользователей"))); }
        if (parameters.containsKey("Видимость трудозатрат")) {
           role.setTimeEntriesVisibility(TimeEntriesVisibility.of(parameters.get("Видимость трудозатрат")));
        }



            Context.put(roleStashId, role);
        }
}