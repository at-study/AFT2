package redmine.model;

import io.qameta.allure.Step;

public interface Generatable<T> {

    @Step("Получена/считана сущность")
    T read();

    @Step("Изменена сущность")
    T update();

    @Step("Создана сущность")
    T create();

    @Step("Сгенерирована сущность")
    default T generate() {
        if (read() != null) {
            return update();
        } else {
            return create();
        }
    }
}
