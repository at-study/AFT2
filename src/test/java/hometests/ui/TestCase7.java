package hometests.ui;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;
import static redmine.utils.Asserts.assertEquals;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redmine.model.user.User;
import redmine.ui.pages.AdminPage;
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

    @Test(testName = " Администрирование. Сортировка списка пользователей по имени  и фамилии", priority = 7, description = "Администрирование. Сортировка списка пользователей по имени  и фамилии")
    @Description("7. Администрирование. Сортировка списка пользователей по имени и фамилии")
    public void usersSortingForAdminByUserNameAndLastName() {
        getPage(LoginPage.class).login(userAdmin.getLogin(), userAdmin.getPassword());
        assertEquals(getPage(HeaderPage.class).administration(), "Администрирование");
        getPage(HeaderPage.class).administration.click();
        assertEquals(getPage(AdminPage.class).adminPageName(), "Администрирование");
        assertEquals(getPage(AdminPage.class).users(), "Пользователи");
        getPage(AdminPage.class).users.click();
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
        private void usersOrderedByLastNameAsc () {
            List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByLastNames
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            List<String> expectedOrderedByAscList = actualOrderedByAscList
                    .stream()
                    .sorted((String s1,String s2)->{ return s1.compareTo(s2);})
                    .collect(Collectors.toList());

            Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
        }
    @Step("Сортировка пользователей по убыванию после клика для переключения порядка Фамилии")
    private void usersOrderedByLastNameDesc () {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByLastNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }

    @Step("Сортировка пользователей по возрастанию после клика для переключения порядка Имени")
    private void usersOrderedByNameAsc () {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByNames
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        List<String> expectedOrderedByAscList = actualOrderedByAscList
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        Assert.assertEquals(actualOrderedByAscList, expectedOrderedByAscList);
    }
    @Step("Сортировка пользователей по убыванию после клика для переключения порядка Имени")
    private void usersOrderedByNameDesc () {
        List<String> actualOrderedByAscList = getPage(UsersPage.class).listOfUsersInTableByNames
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
        private void orderSwitchUserName () {
            getPage(UsersPage.class).usersHeaderInTable.click();
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
