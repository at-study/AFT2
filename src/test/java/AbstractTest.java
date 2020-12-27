import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

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
}
