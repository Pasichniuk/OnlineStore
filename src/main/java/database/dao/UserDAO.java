package database.dao;

import entity.User;
import database.DBConnectionUtil;

import java.util.*;
import java.sql.*;

/**
 * Data accessor object for User entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class UserDAO {

    private static final String SQL_AUTHORIZE_USER = "SELECT user_login, block_status FROM online_store.store_user " +
            "WHERE user_login=? AND user_password=aes_encrypt(?, 'password')";

    private static final String SQL_INSERT_USER = "INSERT INTO online_store.store_user(user_login, user_role, user_password, user_name, user_name_ru) " +
            "VALUES(?, 1, aes_encrypt(?, 'password'), ?, ?)";

    private static final String SQL_FIND_ALL_USERS = "SELECT user_id, user_login, block_status, user_name, user_name_ru FROM online_store.store_user " +
            "WHERE user_role=1";

    private static final String SQL_GET_USER = "SELECT user_id, user_login, block_status, role_name, user_name, user_name_ru FROM online_store.store_user " +
            "JOIN online_store.role ON store_user.user_role = role.role_id WHERE user_login=?";

    private static final String SQL_UPDATE_USER_BLOCK_STATUS = "UPDATE online_store.store_user SET block_status=? WHERE user_id=?";

    private final Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UserDAO() {
        connection = DBConnectionUtil.getConnection();
    }

    /**
     * Authorizes User.
     *
     * @param userLogin Login of User.
     * @param userPassword Password of User.
     *
     * @return If User was authorized.
     */
    public boolean authorizeUser(String userLogin, String userPassword) {
        try {
            preparedStatement = connection.prepareStatement(SQL_AUTHORIZE_USER);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userPassword);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString(2).equals("UNBLOCKED"))
                return true;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * Registers new User.
     *
     * @param userLogin User's login.
     * @param userPassword User's password.
     * @param userName Username.
     * @param userNameRU Username in russian.
     *
     * @return If User was registered.
     */
    public boolean registerUser(String userLogin, String userPassword, String userName, String userNameRU) {
        try {
            if (getUser(userLogin) != null)
                return false;

            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userPassword);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, userNameRU);

            if (preparedStatement.executeUpdate() == 1)
                return true;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    /**
     * Returns users on page.
     *
     * @param offset Offset.
     * @param recordsPerPage Amount of records on page.
     *
     * @return List of user entities.
     */
    public List<User> getUsersOnPage(int offset, int recordsPerPage) {
        List<User> users = getAllUsers();
        List<User> usersOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + recordsPerPage); i++) {

            if (i < users.size())
                usersOnPage.add(users.get(i));
        }

        return usersOnPage;
    }

    /**
     * Returns all users.
     *
     * @return List of user entities.
     */
    public List<User> getAllUsers() {
        List<User> users = null;
        User user;

        try {
            Statement st = connection.createStatement();

            resultSet = st.executeQuery(SQL_FIND_ALL_USERS);

            users = new ArrayList<>();

            while (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        "ROLE_USER", resultSet.getString(4), resultSet.getString(5));
                users.add(user);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return users;
    }

    /**
     * Returns User by login.
     *
     * @param userLogin Login of User.
     *
     * @return User entity.
     */
    public User getUser(String userLogin) {
        User user = null;

        try {
            preparedStatement = connection.prepareStatement(SQL_GET_USER);
            preparedStatement.setString(1, userLogin);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * Updates block status of specific User.
     *
     * @param blockStatus Block status.
     * @param userID User identifier.
     */
    public void updateUserBlockStatus(String blockStatus, int userID) {
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BLOCK_STATUS);
            preparedStatement.setString(1, blockStatus);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
