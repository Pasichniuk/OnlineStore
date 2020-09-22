package dao;

import util.DBConnectionUtil;
import java.sql.*;

public class UserDAO {

    private static final String SQL_GET_USER_LOGIN = "SELECT user_login FROM online_store.store_user " +
            "WHERE user_login=? AND user_password=aes_encrypt(?, 'password')";

    private static final String SQL_INSERT_USER = "INSERT INTO online_store.store_user(user_login, user_role, user_password) " +
            "VALUES(?, 1, aes_encrypt(?, 'password'))";

    private static final String SQL_GET_USER_ID = "SELECT user_id FROM online_store.store_user WHERE user_login=?";

    private Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public boolean authorizeUser(String userLogin, String userPassword) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_GET_USER_LOGIN);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userPassword);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return true;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public boolean registerUser(String userLogin, String userPassword) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userPassword);

            if (preparedStatement.executeUpdate() == 1)
                return true;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public int getUserID(String userLogin) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_GET_USER_ID);
            preparedStatement.setString(1, userLogin);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }
}
