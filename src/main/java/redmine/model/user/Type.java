package redmine.model.user;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Type {
    USER(""),
    GROUPNONMEMBER(""),
    GROUPANONYMOUS(""),
    ANONYMOUSUSER("");

    public final String description;

    public static Type of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден Type по описанию  " + description));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
