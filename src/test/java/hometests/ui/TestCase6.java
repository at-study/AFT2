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
import redmine.utils.Asserts;
import redmine.utils.BrowserUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase6 {
    private User userAdmin;
    private User user1;
    private User user2;
    private User user3;


    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        user1 = new User().setAdmin(false).setStatus(1).generate();
        user2 = new User().setAdmin(false).setStatus(1).generate();
        user3 = new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Сортировка списка пользователей по пользователю", priority = 7, description = "Администрирование. Сортировка списка пользователей по пользователю")
    @Description("6. Администрирование. Сортировка списка пользователей по пользователю")
    public void usersSortingForAdminByUser() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        Asserts.assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        Asserts.assertEquals(getPage(AdminPage.class).adminPageName(), "Администрирование");
        Asserts.assertEquals(getPage(AdminPage.class).users(), "Пользователи");
        getPage(AdminPage.class).users.click();
        Asserts.assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(UsersPage.class).table));
        userSortingByAscending();
        orderSwitch();
        userSortingByDescending();
    }

    @Step("Сортировка пользователей по возрастанию при заходе на страницу")
    private void userSortingByAscending() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTable
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("Сортировка пользователей по убыванию после клика для переключения порядка")
    private void userSortingByDescending() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTable
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
    private void orderSwitch() {
        getPage(UsersPage.class).usersHeaderInTable.click();
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }

}

