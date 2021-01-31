package redmine.model.user;

import lombok.*;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Language {
    RU("Russian(Русский)"),
    EN("English(Английский)");

    public final String description;

    public static Language of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден Language по описанию " + description));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
