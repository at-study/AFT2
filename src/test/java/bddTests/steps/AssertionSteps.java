package bddTests.steps;

import cucumber.api.java.ru.Тогда;
import lombok.SneakyThrows;
import org.testng.Assert;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.role.*;
import java.util.Map;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static org.testng.Assert.assertEquals;

public class AssertionSteps {
    @SneakyThrows
    @Тогда("Роль {string} имеет параметры:")
    public void assertRoleParameters(String roleStashId, Map<String, String> parameters) {
        Role role = Context.get(roleStashId, Role.class);
        ParametersValidator.validateRoleParameters(parameters);
        if (parameters.containsKey("Позиция")) {
            assertEquals(role.getPosition(), valueOf(parseInt(parameters.get("Позиция"))));
        }
        if (parameters.containsKey("Встроенная")) {
            assertEquals(role.getBuiltin(), valueOf(parseInt(parameters.get("Встроенная"))));
        }
        if (parameters.containsKey("Задача может быть назначена этой роли")) {
            assertEquals(role.getAssignable(), Boolean.valueOf(parseBoolean(parameters.get("Задача может быть назначена этой роли"))));
        }
        if (parameters.containsKey("Видимость задач")) {
            assertEquals(role.getIssuesVisibility(), IssuesVisibility.of(parameters.get("Видимость задач")));
        }
        if (parameters.containsKey("Видимость пользователей")) {
            assertEquals(role.getUsersVisibility(), UsersVisibility.of(parameters.get("Видимость пользователей")));
        }
        if (parameters.containsKey("Видимость трудозатрат")) {
            assertEquals(role.getTimeEntriesVisibility(), TimeEntriesVisibility.of(parameters.get("Видимость трудозатрат")));
        }
        if (parameters.containsKey("Права")) {
            RolePermissions expectedPermissions = Context.get(parameters.get("Права"), RolePermissions.class);
            RolePermissions actualPermissions = role.getPermissions();
            Assert.assertEquals(actualPermissions, expectedPermissions);
        }
    }
}
