package hometests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.*;
import redmine.utils.BrowserUtils;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase6 {
    private User userAdmin;

    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        new User().setAdmin(false).setStatus(1).generate();
        new User().setAdmin(false).setStatus(1).generate();
        new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Сортировка списка пользователей по пользователю", description = "Администрирование. Сортировка списка пользователей по пользователю")
    @Description("6. Администрирование. Сортировка списка пользователей по пользователю")
    public void usersSortingByAdminByUser() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).adminHomePage));
        getPage(HeaderPage.class).administration.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).administration));
        getPage(AdministrationPage.class).users.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(UsersPage.class).table));
        sortUsersByUsernameAsc();
        switchOrderByUsername();
        sortUsersByUsernameDesc();
    }

    @Step("Сортировка пользователей по возрастанию при заходе на страницу")
    private void sortUsersByUsernameAsc() {
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

    @Step("Сортировка пользователей по убыванию после клика для переключения порядка")
    private void sortUsersByUsernameDesc() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByUsername
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("В шапке таблицы нажать на 'Пользователь'")
    private void switchOrderByUsername() {
        getPage(UsersPage.class).usersHeaderInTable.click();
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }

}

