package redmine.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import redmine.ui.pages.helpers.CucumberName;

@CucumberName("Заголовок")
public class HeaderPage extends AbstractPage {

    @CucumberName("Домашняя страница")
    @FindBy(xpath = "//a[@class='home']")
    public WebElement home;

    @CucumberName("Проекты")
    @FindBy(xpath = "//a[@class='projects']")
    public WebElement projects;

    @CucumberName("Вошли как")
    @FindBy(xpath = "//div[@id='loggedas']")
    public WebElement loggedAs;

    @CucumberName("Активный пользователь")
    @FindBy(xpath = "//a[@class='user active']")
    public WebElement activeUser;

    @FindBy(xpath = "//a[@class='home']")
    public WebElement adminHomePage;

    @CucumberName("Моя страница")
    @FindBy(xpath = "//a[@class='my-page']")
    public WebElement myPage;

    @CucumberName("Администрирование")
    @FindBy(xpath = "//a[@class='administration']")
    public WebElement administration;

    @CucumberName("Помощь")
    @FindBy(xpath = "//a[@class='help']")
    public WebElement help;

    @CucumberName("Моя учётная запись")
    @FindBy(xpath = "//a[@class='my-account']")
    public WebElement myAccount;

    @CucumberName("Выйти")
    @FindBy(xpath = "//a[@class='logout']")
    public WebElement logout;

    @CucumberName("Лейбл поиск")
    @FindBy(xpath = "//form/label/a")
    public WebElement searchLabel;

    @CucumberName("Поле поиск")
    @FindBy(xpath = "//form/input[@id='q']")
    public WebElement searchField;

    @CucumberName("Войти")
    @FindBy(xpath = "//a[@class='login']")
    public WebElement signIn;

    @CucumberName("Регистрация")
    @FindBy(xpath = "//a[@class='register']")
    public WebElement register;

    @Step("Присутствует текст 'Вошли как <Логин>'")
    public String loggedAs() {
        return loggedAs.getText();
    }


    @Step("Присутствует элемент 'Помощь'")
    public String help() {
        return help.getText();
    }

    @Step("Присутствует элемент 'Поиск'")
    public String searchLabel() {
        return searchLabel.getText();
    }

}
