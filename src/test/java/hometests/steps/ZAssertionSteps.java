package hometests.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.model.role.IssuesVisibility;
import redmine.model.role.Role;
import redmine.model.role.TimeEntriesVisibility;
import redmine.model.role.UsersVisibility;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.BrowserUtils;

import java.util.Map;

import static java.lang.Boolean.*;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static org.testng.Assert.assertEquals;

public class ZAssertionSteps {
    @То("Значение переменной {string} равно  {int}")
    public void assertResult(String stashId, int expected) {
        int res = Context.get(stashId, Integer.class);
        assertEquals(res, expected);
    }

    @То("На главной странице отображается поле {string}")
    public void assertProjectElementIsDisplayed(String fieldName) {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Заголовок", fieldName)));
    }

    @То("На главной странице не отображается поле {string}")
    public void assertProjectElementIsNotDisplayed(String fieldName) {
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy("Заголовок", fieldName)));
    }

    @И("На странице {string} отображается элемент {string}")
    public void assertFieldIsDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(element));
    }

    @И("На странице {string} не отображается элемент {string}")
    public void assertFieldIsNotDisplayed(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(element));
    }

    @Тогда("Роль {string} имеет параметры:")
    public void assertRoleParameters(String roleStashId, Map<String, String> parameters) {
        Role role = Context.get(roleStashId, Role.class);
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
    }
}
