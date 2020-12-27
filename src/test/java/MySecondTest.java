import org.testng.annotations.Test;

public class MySecondTest extends AbstractTest {
    /**
     * Исполнение по порядку не гарантировано-по алфавиту
     * У афтеров логика такая же только в обратном порядке,так как после
     */
    @Test(testName = "Мой третий тест", description = "Мой третий тест")
    public void myThirdTest() {
        System.out.println("3");
    }

    @Test(testName = "Мой четвёртый  тест", description = "Мой четвёртый тест")
    public void myFourthTest() {
        System.out.println("4");
    }

}
