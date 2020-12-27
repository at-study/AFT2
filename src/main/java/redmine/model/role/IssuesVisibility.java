package redmine.model.role;

import java.util.stream.Stream;

public enum IssuesVisibility {
    ALL("Все задачи"),
    DEFAULT("Только общие задачи"),
    OWN("Задачи созданные или назначенные пользователю");

    public final String description;

    IssuesVisibility(String description) {
        this.description = description;
    }

    public static IssuesVisibility of(final String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден IssuesVisibility по описанию " + description));
    }
}
