package database;

import java.sql.*;

/**
 * Provides connection to database.
 *
 * @author Vlad Pasichniuk
 *
 */

public class DBConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/online_store";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "10082002";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;

        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            return connection;

        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

}
