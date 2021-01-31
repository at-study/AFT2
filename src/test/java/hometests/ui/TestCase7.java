package hometests.ui;

import static redmine.utils.Asserts.assertEquals;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.AdministrationPage;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.LoginPage;
import redmine.ui.pages.UsersPage;
import redmine.utils.BrowserUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.*;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.Pages.getPage;

public class TestCase7 {
    private User userAdmin;

    @BeforeMethod
    public void prepareFixture() {
        userAdmin = new User().setAdmin(true).setStatus(1).generate();
        new User().setAdmin(false).setStatus(1).generate();
        new User().setAdmin(false).setStatus(1).generate();
        new User().setAdmin(false).setStatus(1).generate();
        openPage("login");
    }

    @Test(testName = " Администрирование. Сортировка списка пользователей по имени  и фамилии", priority = 7, description = "Администрирование. Сортировка списка пользователей по имени  и фамилии")
    @Description("7. Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void usersSortingForAdminByUserNameAndLastName() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        assertEquals(getPage(AdministrationPage.class).adminPageName(), "Администрирование");
        assertEquals(getPage(AdministrationPage.class).users(), "Пользователи");
        getPage(AdministrationPage.class).users.click();
        assertEquals(getPage(UsersPage.class).usersPageName(), "Пользователи");
        assertTrue(BrowserUtils.isElementPresent(getPage(UsersPage.class).table));
        tableNotSortedByLastName();
        tableNotSortedByName();
        orderSwitchUserLastName();
        usersOrderedByLastNameAsc();
        tableNotSortedByName();
        orderSwitchUserLastName();
        usersOrderedByLastNameDesc();
        tableNotSortedByName();
        orderSwitchName();
        tableNotSortedByLastName();
        usersOrderedByNameAsc();
        orderSwitchName();
        tableNotSortedByLastName();
        usersOrderedByNameDesc();
    }

    @Step("Таблица не отсортирована по фамилии")
    private void tableNotSortedByLastName() {
        List<String> actualNonOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByLastNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> notExpectedOrderedByAscList = actualNonOrderedByAscList
                .stream()
                .sorted()
                .collect(Collectors.toList());

        Assert.assertNotEquals(actualNonOrderedByAscList, notExpectedOrderedByAscList);
    }

    @Step("Таблица не отсортирована по имени")
    private void tableNotSortedByName() {
        List<String> actualNonOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> notExpectedOrderedByAscList = actualNonOrderedByAscList
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        Assert.assertNotEquals(actualNonOrderedByAscList, notExpectedOrderedByAscList);
    }

    @Step("Сортировка пользователей по возрастанию после клика для переключения порядка Фамилии")
    private void usersOrderedByLastNameAsc() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByLastNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("Сортировка пользователей по убыванию после клика для переключения порядка Фамилии")
    private void usersOrderedByLastNameDesc() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByLastNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("Сортировка пользователей по возрастанию после клика для переключения порядка Имени")
    private void usersOrderedByNameAsc() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("Сортировка пользователей по убыванию после клика для переключения порядка Имени")
    private void usersOrderedByNameDesc() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("В шапке таблицы нажать на 'Фамилии'")
    private void orderSwitchUserLastName() {
        getPage(UsersPage.class).usersByLastNameHeaderInTable.click();
    }

    @Step("В шапке таблицы нажать на 'Имя'")
    private void orderSwitchName() {
        getPage(UsersPage.class).usersByNameHeaderInTable.click();
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
