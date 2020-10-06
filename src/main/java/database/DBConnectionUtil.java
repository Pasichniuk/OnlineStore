package database;

import constant.Constants;
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
            Class.forName(Constants.DRIVER);

            connection = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

            return connection;

        } catch (ClassNotFoundException | SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

}
