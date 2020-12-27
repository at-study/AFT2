import org.testng.annotations.BeforeMethod;

public class AbstractTest {

    @BeforeMethod
    public void prepareFixture() {
        System.out.println("BeforeMethod- предусловие перед методом");
    }
}
