package database;

import database.dao.CategoryDAO;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Provides connection to database.
 *
 * Singleton.
 *
 * @author Vlad Pasichniuk
 *
 */

public class DBConnectionUtil {

    private static final Logger logger = Logger.getLogger(CategoryDAO.class);

    private static final String URL = "jdbc:mysql://localhost:3306/online_store";

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "10082002";

    private static DBConnectionUtil instance;

    private Connection connection;

    private DBConnectionUtil() { }

    public static DBConnectionUtil getInstance() {
        if (instance == null)
            instance = new DBConnectionUtil();

        return instance;
    }

    public Connection getConnection() {
        if (connection != null)
            return connection;

        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            return connection;

        } catch (ClassNotFoundException | SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

}
