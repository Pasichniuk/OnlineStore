import util.WelcomeTag;
import org.junit.*;

import static org.junit.Assert.*;

public class WelcomeTagTest {

    @Test
    public void doTagTest() {
        WelcomeTag welcomeTag = new WelcomeTag();
        assertNotNull(welcomeTag);
    }
}
