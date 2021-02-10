package bddTests.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.model.project.Project;
import redmine.model.user.User;
import redmine.ui.pages.ProjectsPage;
import redmine.ui.pages.UsersPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import java.util.List;
import java.util.stream.Collectors;

import static redmine.ui.pages.helpers.Pages.getPage;

public class ElementAssertionSteps {
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
    @SneakyThrows
    @То("Отображается сообщение {string}{string}{string}")
    public void assertCreationMessage(String user,String userDataStashId,String created) {
        User userContext= Context.get(userDataStashId, User.class);
        String result=String.format("%s %s %s",user,userContext.getLogin(),created);
        WebElement element = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Уведомление о создании нового пользователя");
        Assert.assertEquals(element.getText(),result);
    }

    @И("Отображается проект {string}")
    public void assertProjectNameAndDescriptionDisplayed(String projectStashId) {
        Project project = Context.get(projectStashId, Project.class);
        String projectExpectedName=project.getName();
        String projectExpectedDescription=project.getDescription();
        String actualName= ProjectsPage.projectName(projectExpectedName);
        String actualDescription=ProjectsPage.projectNameDescription(projectExpectedName);
        Asserts.assertEquals(actualName, projectExpectedName);
        Asserts.assertEquals(actualDescription, projectExpectedDescription);
    }

    @И("{string} не отсортирована по {string}")
    public void assertUnSorting(String tableStashId,String sortByElement){}
    @И("{string} отсортирована по {string} по убыванию")
    public void assertSortingByDesc(String tableStashId,String sortByElement){}
    @И("{string} отсортирована по {string} по возрастанию")
    public void assertSortingByAsc(String tableStashId,String sortByElement){


        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByUsername
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

}
