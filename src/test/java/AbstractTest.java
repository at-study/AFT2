import org.testng.annotations.*;

public class AbstractTest {

    @BeforeClass
    public void prepareFixture() {
        System.out.println("BeforeClass- предусловие перед классом");
    }
    @BeforeMethod
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
    @AfterMethod
    public void prepareFixtureForMethodAfter() {
        System.out.println("AfterMethod- постусловие после методом");
    }
    @AfterSuite
    public void prepareFixtureForSuiteAfter() {
        System.out.println("AfterSuite- постусловие после сьютой");
    }
}
