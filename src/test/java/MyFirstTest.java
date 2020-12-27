import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MyFirstTest {

    @BeforeMethod
    public void prepareFixture(){
        System.out.println("1");
    }

    @Test(testName = "Первый тест",description = "Имя первого теста")
    public void myFirstTest(){
        System.out.println("2");
    }
    @Test(testName = "Второй тест",description = "Имя второго теста")
    public void mySecondTest(){
        System.out.println("3");
    }
}
