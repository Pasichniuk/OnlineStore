package databasetest;

import database.DBConnectionUtil;
import org.junit.*;

import static org.junit.Assert.*;

public class DBConnectionUtilTest {

    @Test
    public void getConnectionTest() {
        assertNotNull(DBConnectionUtil.getInstance().getConnection());
        assertNotNull(DBConnectionUtil.getInstance().getConnection());
    }
}
