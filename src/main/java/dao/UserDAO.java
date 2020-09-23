package dao;

import entity.User;
import util.DBConnectionUtil;

import java.util.*;
import java.sql.*;

public class UserDAO {

    private static final String SQL_GET_USER_LOGIN = "SELECT user_login FROM online_store.store_user " +
            "WHERE user_login=? AND user_password=aes_encrypt(?, 'password')";

    private static final String SQL_INSERT_USER = "INSERT INTO online_store.store_user(user_login, user_role, user_password) " +
            "VALUES(?, 1, aes_encrypt(?, 'password'))";

    private static final String SQL_FIND_ALL_USERS = "SELECT user_id, user_login, block_status FROM online_store.store_user " +
            "WHERE user_role=1";

    private static final String SQL_GET_USER = "SELECT user_id, user_login, block_status, role_name FROM online_store.store_user " +
            "JOIN online_store.role ON store_user.user_role = role.role_id WHERE user_login=?";

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

    public List<User> getAllUsers() {
        List<User> users = null;
        User user;

        try {
            connection = DBConnectionUtil.getConnection();

            Statement st = connection.createStatement();

            resultSet = st.executeQuery(SQL_FIND_ALL_USERS);

            users = new ArrayList<>();

            while (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), "ROLE_USER");
                users.add(user);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return users;
    }

    public User getUser(String userLogin) {
        User user = null;

        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_GET_USER);
            preparedStatement.setString(1, userLogin);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }
}
