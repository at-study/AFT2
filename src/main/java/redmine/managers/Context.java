package redmine.managers;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class Context {

    private static ThreadLocal<Stash> stash=new ThreadLocal<>();

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
        if (stash.get()== null) {
            stash.set(new Stash());
        }
        return stash.get();
    }

    public static void clearStash() {
        if (stash.get() != null) {
            stash.set(null);
        }
    }

    @Step("Сущность в контексте автотеста")
    public static void saveStashToAllure() {
        getStash().getEntities().forEach((key, value) -> Allure.addAttachment(key, value.toString()));
    }
}
