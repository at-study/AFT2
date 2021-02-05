package redmine.cucumber;

import org.testng.Assert;

import java.util.Map;

public class ParametersValidator {
    public static void validateRoleParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> Assert.assertTrue(AllowedParameters.ROLE_PARAMETERS.contains(key),
                "Список допустимых параметров пр работе с ролями не содержит параметр" + key));
    }

    public static void validateUserParameters(Map<String, String> parameters) {

    }
}
