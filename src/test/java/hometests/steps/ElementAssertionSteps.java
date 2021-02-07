package hometests.steps;

import cucumber.api.java.ru.И;
import cucumber.api.java.ru.То;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.model.user.User;
import redmine.ui.pages.HeaderPage;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.BrowserUtils;
import static redmine.ui.pages.helpers.Pages.getPage;
import static redmine.utils.Asserts.assertEquals;

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

    @И("Отображается Вошли как {string}")
    private void assertLoggedAsElement(String userStashId) {
        User user= Context.get(userStashId, User.class);
        WebElement element=CucumberPageObjectHelper.getElementBy("Заголовок","Вошли как");
        assertEquals(getPage(HeaderPage.class).loggedAs(), "Вошли как " + user.getLogin());
    }
}
