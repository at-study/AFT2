package redmine.ui;

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

import java.util.List;
import java.util.stream.Collectors;

import static redmine.managers.Manager.driverQuit;
import static redmine.managers.Manager.openPage;
import static redmine.ui.pages.helpers.Pages.getPage;

public class UiTestCase6 {
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
        displayOfHomePage();
        displayOfAdministrationPage();
        displayOfUsersTable();
        sortUsersByUsernameAsc();
        switchOrderByUsername();
        sortUsersByUsernameDesc();
    }

    @Step("1. Отображается домашняя страница")
    private void displayOfHomePage() {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).adminHomePage));
    }

    @Step("2. Отображается страница \"Администрирование\"")
    private void displayOfAdministrationPage() {
        getPage(HeaderPage.class).administration.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(HeaderPage.class).administration));
    }

    @Step("3.Отображается таблица с пользователями")
    private void displayOfUsersTable() {
        getPage(AdministrationPage.class).users.click();
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(getPage(UsersPage.class).table));
    }

    @Step("4.Сортировка пользователей по возрастанию при заходе на страницу")
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

    @Step("5.Сортировка пользователей по убыванию после клика для переключения порядка")
    private void sortUsersByUsernameDesc() {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByUsername
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(String.CASE_INSENSITIVE_ORDER.reversed())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    private void switchOrderByUsername() {
        getPage(UsersPage.class).usersHeaderInTable.click();
    }

    @AfterMethod
    public void tearDown() {
        driverQuit();
    }

}

