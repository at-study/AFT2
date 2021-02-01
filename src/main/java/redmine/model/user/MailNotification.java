package redmine.model.user;

import lombok.*;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum MailNotification {
    ALL("О всех событиях во всех моих проектах"),
    ONLY_MY_EVENTS("Только для объектов ,которые я отслеживаю или в которых учавствую"),
    SELECTED("О событиях только в выбранном проекте..."),
    ONLY_ASSIGNED("Только для объектов ,которые я отслеживаю или которые мне назначены"),
    ONLY_OWNER("Только для объектов ,которые я отслеживаю или для которых я владелец"),
    NONE("Нет событий");

    public final String description;

    public static MailNotification of(String description) {
        return Stream.of(values())
                .filter(it -> it.description.equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Не найден MailNotification по описанию  " + description));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
