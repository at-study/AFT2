package hometests.ui;

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

import static org.testng.Assert.assertTrue;
import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.helpers.Pages.getPage;


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

    @Test(testName = " Администрирование. Сортировка списка пользователей по имени  и фамилии", description = " Администрирование. Сортировка списка пользователей по имени  и фамилии")
    @Description("7. Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void usersSortingByAdminByUserNameAndLastName() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        displayOfHomePage();
        displayOfAdministrationPage();
        displayOfTableWithUsers();
        assertTableNotSortedByLastName();
        assertTableNotSortedByName();
        switchOrderByLastName();
        sortUsersByLastNameAsc();
        assertTableNotSortedByName();
        switchOrderByLastName();
        sortUsersByLastNameDesc();
        assertTableNotSortedByName();
        switchOrderByName();
        assertTableNotSortedByLastName();
        sortUsersByNameAsc();
        switchOrderByName();
        assertTableNotSortedByLastName();
        sortUsersByNameDesc();
    }

    @Step("1. Отображается домашняя страница")
    private void displayOfHomePage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).home));
    }

    @Step("2. Отображается страница \"Администрирование\"")
    private void displayOfAdministrationPage() {
        getPage(HeaderPage.class).administration.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(AdministrationPage.class).adminPageName));
    }

    @Step("3. Отображается таблица с пользователями")
    private void displayOfTableWithUsers() {
        getPage(AdministrationPage.class).users.click();
        assertTrue(BrowserUtils.isElementPresent(getPage(UsersPage.class).table));
    }

    @Step("4.Таблица не отсортирована по Фамилии")
    private void assertTableNotSortedByLastName() {
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

    @Step("5.Таблица не отсортирована по имени")
    private void assertTableNotSortedByName() {
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
    private void sortUsersByLastNameAsc() {
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
    private void sortUsersByLastNameDesc() {
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
    private void sortUsersByNameAsc() {
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
    private void sortUsersByNameDesc() {
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
    private void switchOrderByLastName() {
        getPage(UsersPage.class).usersByLastNameHeaderInTable.click();
    }

    @Step("В шапке таблицы нажать на 'Имя'")
    private void switchOrderByName() {
        getPage(UsersPage.class).usersByNameHeaderInTable.click();
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }
}
