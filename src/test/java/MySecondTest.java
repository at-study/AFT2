import org.testng.annotations.Test;

public class MySecondTest extends AbstractTest {
    @Test(testName = "Мой третий тест", description = "Мой третий тест")
    public void myThirdTest() {
        System.out.println("3");
    }
}
