package redmine.ui.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;
import redmine.managers.Manager;

public class Pages {

    @SneakyThrows
    public static <T extends AbstractPage> T getPage(Class<T> clazz) {
        T page = clazz.newInstance();
        PageFactory.initElements(Manager.driver(), page);
        return page;
    }
}
