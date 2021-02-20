package bddTests.steps;

import cucumber.api.java.ru.Если;
import cucumber.api.java.ru.И;
import org.openqa.selenium.WebElement;
import redmine.managers.Context;
import redmine.model.user.User;
import redmine.ui.pages.helpers.CucumberPageObjectHelper;
import java.util.Objects;
import static redmine.utils.StringGenerators.randomEmail;

public class ElementSteps {
    @И("На странице {string} нажать на элемент {string}")
    public void clickOnElement(String pageName, String fieldName) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        element.click();
    }

    @И("На странице {string} в поле {string} ввести текст {string}")
    public void textInputToField(String pageName, String fieldName, String text) {
        WebElement element = CucumberPageObjectHelper.getElementBy(pageName, fieldName);
        element.sendKeys(text);
    }

    @Если("Заполнить данные пользователя корректными значениями и сохраняем в переменную {string}")
    public void generateAndFillNewUserForm(String userDataStashId) {
        User createdUser = new User();
        String mail = randomEmail();
        WebElement elementLogin = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Пользователь");
        elementLogin.click();
        elementLogin.sendKeys(createdUser.getLogin());
        WebElement elementName = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Имя");
        elementName.click();
        elementName.sendKeys(createdUser.getFirstName());
        WebElement elementLastName = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Фамилия");
        elementLastName.click();
        elementLastName.sendKeys(createdUser.getLastName());
        WebElement elementMail = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", "Электронная почта");
        elementMail.click();
        elementMail.sendKeys(mail);
        Context.put(userDataStashId, createdUser);
    }

    @И("Установить чекбокс {string}")
    public void setCreatePassword(String fieldName) {
        WebElement elementMail = CucumberPageObjectHelper.getElementBy("Страница создания нового пользователя", fieldName);
        elementMail.click();
    }

    @Если("В шапке {string} {string}")
    public void pushTableHeader(String tableStashId, String fieldElement) {
        if (Objects.equals(fieldElement, "нажать на Фамилия")) {
            WebElement pushFamily = CucumberPageObjectHelper.getElementBy("Страница Пользователи", "нажать на Фамилия");
            pushFamily.click();
        }
        if (Objects.equals(fieldElement, "нажать на Имя")) {
            WebElement pushName = CucumberPageObjectHelper.getElementBy("Страница Пользователи", "нажать на Имя");
            pushName.click();
        }
        if (Objects.equals(fieldElement, "нажать на Пользователь")) {
            WebElement pushUser = CucumberPageObjectHelper.getElementBy("Страница Пользователи", "нажать на Пользователь");
            pushUser.click();
        }
    }
}
