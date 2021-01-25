package redmine.ui.pages;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;
import redmine.managers.Manager;

import java.io.ByteArrayInputStream;

public class Pages {

    @SneakyThrows
    public static <T extends AbstractPage> T getPage(Class<T> clazz) {
        return Allure.step("Обращение к странице " + clazz.getSimpleName(), () -> {
            T page = clazz.newInstance();
            PageFactory.initElements(Manager.driver(), page);
            Allure.addAttachment("screenshot", new ByteArrayInputStream(Manager.takesScreenshot()));
            return page;
        });
    }
}