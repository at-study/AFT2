import org.testng.annotations.*;

public class AbstractTest {

    @BeforeClass
    public void prepareFixture() {
        System.out.println("BeforeClass- предусловие перед классом");
    }

    /**
     * по умолчанию олвейс ран фолсе,в тру надо переводить.даже если тест падает всё равно продолжается выполнение
     */
    @BeforeMethod(alwaysRun = true)
    public void prepareFixtureForMethod() {
        System.out.println("BeforeMethod- предусловие перед методом");
    }
    @BeforeSuite
    public void prepareFixtureForSuite() {
        System.out.println("BeforeSuite- предусловие перед сьютой");
    }
    @AfterClass
    public void prepareFixtureAfter() {
        System.out.println("AfterClass- постусловие после классом");
    }
    @AfterMethod(alwaysRun = true)
    public void prepareFixtureForMethodAfter() {
        System.out.println("AfterMethod- постусловие после методом");
    }
    @AfterSuite
    public void prepareFixtureForSuiteAfter() {
        System.out.println("AfterSuite- постусловие после сьютой");
    }
}
