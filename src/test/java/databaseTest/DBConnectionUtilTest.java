package databaseTest;

import database.DBConnectionUtil;
import org.junit.*;

import static org.junit.Assert.*;

public class DBConnectionUtilTest {

    @Test
    public void getConnectionTest() {
        assertNotNull(DBConnectionUtil.getConnection());

        assertNotNull(DBConnectionUtil.getConnection());
    }
}
