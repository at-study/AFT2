package redmine.ui.pages.helpers;

import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.reflections.Reflections;
import redmine.ui.pages.AbstractPage;
import redmine.ui.pages.HeaderPage;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Stream;

public class CucumberPageObjectHelper {
    @SneakyThrows
    public static WebElement getElementBy(String cucumberFieldName) {
        HeaderPage page = Pages.getPage(HeaderPage.class);
        Field founfField = Stream.of(page.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CucumberName.class))
                .filter(field -> cucumberFieldName.equals(field.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Нет аннотации @CucumberName(\"%s\")", cucumberFieldName)));
        founfField.setAccessible(true);
        return (WebElement) founfField.get(page);
    }

    @SneakyThrows
    public static AbstractPage getPageBy(String cucumberPageName) {
        Reflections reflections = new Reflections("my.project.prefix");
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(CucumberName.class);

        Class<?> pageClass = allClasses.stream().filter(clazz -> cucumberPageName.equals(clazz.getAnnotation(CucumberName.class).value()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Нет аннотации @CucumberName(\"%s\") у класса", cucumberPageName)));
        return Pages.getPage((Class<AbstractPage>) pageClass);
    }
}
