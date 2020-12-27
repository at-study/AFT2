import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyFirstTest extends AbstractTest {
    /**
     * TestNg первые тесты -лекция 1 (BeforeSuite,BeforeClass,BeforeMethod)
     */

    @Test(testName = "Первый тест", description = "Имя первого теста")
    public void myFirstTest() {
        System.out.println("1");
    }

    @Test(testName = "Второй тест", description = "Имя второго теста")
    public void mySecondTest() {
        System.out.println("2");
    }
}
