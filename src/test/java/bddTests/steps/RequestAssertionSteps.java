package bddTests.steps;

import cucumber.api.java.ru.И;
import redmine.api.interfaces.Response;
import redmine.utils.Asserts;

public class RequestAssertionSteps {
    @И("Получен статус код ответа {int}")
    public void assertAnswerCode(Response actualCode, int expectedCode) {
        Asserts.assertEquals(actualCode.getStatusCode(), expectedCode);
    }
}
