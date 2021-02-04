package hometests.steps;

import cucumber.api.java.ru.То;
import org.testng.Assert;
import redmine.managers.Context;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import redmine.utils.BrowserUtils;

public class ZAssertionSteps {
    @То("Значение переменной {string} равно  {int}")
    public void assertResult(String stashId, int expected) {
        int res = Context.getStash().get(stashId, Integer.class);
        Assert.assertEquals(res, expected);
    }

    @То("На главной странице отображается поле {string}")
    public void assertProjectElementIsDisplayed(String fieldName) {
        Assert.assertTrue(BrowserUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy(fieldName)));
    }

    @То("На главной странице не отображается поле {string}")
    public void assertProjectElementIsNotDisplayed(String fieldName) {
        Assert.assertFalse(BrowserUtils.isElementCurrentlyPresent(CucumberPageObjectHelper.getElementBy(fieldName)));
    }
}
