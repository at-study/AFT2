package hometests.steps;

import cucumber.api.java.ru.Пусть;
import redmine.cucumber.ParametersValidator;
import redmine.managers.Context;
import redmine.model.user.User;

import java.util.Map;

public class GeneratorsSteps {

    @Пусть("В системе существует пользователь {string} с параметрами:")
    public void generateAndSaveUser(String stashId, Map<String, String> parameters) {
        ParametersValidator.validateUserParameters(parameters);
        User user = new User();
        if (parameters.containsKey("Администратор")) {
            user.setAdmin(Boolean.parseBoolean(parameters.get("Администратор")));
            if (parameters.containsKey("Статус")) {
                user.setStatus(Integer.parseInt(parameters.get("Статус")));
            }
            user.generate();
            Context.put(stashId, user);
        }
    }

}
