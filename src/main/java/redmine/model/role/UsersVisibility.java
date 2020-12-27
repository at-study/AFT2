package redmine.model.role;

import java.util.stream.Stream;

public enum UsersVisibility {
    ALL("Все активные пользователи"),
    MEMBERS_OF_VISIBLE_PROJECTS("Участники видимых проектов");

    public final String description;

    public static UsersVisibility of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден UsersVisibility по описанию " + description));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
