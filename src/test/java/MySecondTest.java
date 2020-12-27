import org.testng.annotations.Test;

public class MySecondTest extends AbstractTest {
    /**
     * Исполнение по порядку не гарантировано-по алфавиту
     * У афтеров логика такая же только в обратном порядке,так как после
     */
    @Test(testName = "Мой третий тест", description = "Мой третий тест",groups = {"group1"},priority = 10)
    public void myThirdTest() {
        System.out.println("3");
    }

    /**
     * groups1
     * priority-низкие вначале
     * expectedException
     */
    @Test(testName = "Мой четвёртый  тест",groups = {"group1"},priority = 20,expectedExceptions = {IllegalStateException.class})
    public void myFourthTest() {
        System.out.println("4");
        throw new IllegalStateException("");
    }

}
