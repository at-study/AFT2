package redmine.managers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.util.Map;

public class Context {

    private static Map<Thread,Stash> stash;

    public static void put(String stashId, Object entity) {
        getStash().put(stashId, entity);
    }

    public static <T> T get(String stashId, Class<T> clazz) {
        return clazz.cast(get(stashId));
    }

    public static Object get(String stashId) {
        return getStash().get(stashId);
    }

    private static Stash getStash() {
        if (stash.get(Thread.currentThread()) == null) {
            stash.put(Thread.currentThread(),new Stash());
        }
        return stash.get(Thread.currentThread());
    }

    public static void clearStash() {
        if (stash != null) {
            stash = null;
        }
    }

    @Step("Сущность в контексте автотеста")
    public static void saveStashToAllure() {
        getStash().getEntities().forEach((key, value) -> Allure.addAttachment(key, value.toString()));
    }
}
