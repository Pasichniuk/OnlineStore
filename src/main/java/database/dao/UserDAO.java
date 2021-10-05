package database.dao;

import java.util.*;
import java.sql.*;

import entity.User;
import database.DBConnectionUtil;

/**
 * Data accessor object for User entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class UserDAO {

    private static final String SQL_AUTHORIZE_USER = "SELECT user_login, block_status FROM online_store.store_user WHERE user_login=? AND user_password=aes_encrypt(?, 'password')";
    private static final String SQL_INSERT_USER = "INSERT INTO online_store.store_user(user_login, user_role, user_password, user_name, user_name_ru) VALUES(?, 1, aes_encrypt(?, 'password'), ?, ?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT user_id, user_login, block_status, user_name, user_name_ru FROM online_store.store_user WHERE user_role=1";
    private static final String SQL_GET_USER = "SELECT user_id, user_login, block_status, role_name, user_name, user_name_ru FROM online_store.store_user JOIN online_store.role ON store_user.user_role = role.role_id WHERE user_login=?";
    private static final String SQL_UPDATE_USER_BLOCK_STATUS = "UPDATE online_store.store_user SET block_status=? WHERE user_id=?";

    private final Connection connection;

    public UserDAO() {
        this.connection = DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Authorizes the user.
     *
     * @param userLogin Login of User.
     * @param userPassword Password of User.
     *
     * @return If User was authorized.
     */
    public boolean authorizeUser(String userLogin, String userPassword) {

        try (var preparedStatement = connection.prepareStatement(SQL_AUTHORIZE_USER)) {

            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userPassword);

            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next() && resultSet.getString(2).equals("UNBLOCKED")) {
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    /**
     * Registers a new user.
     *
     * @param userLogin User's login.
     * @param userPassword User's password.
     * @param userName Username.
     * @param userNameRU Username in russian.
     *
     * @return If User was registered.
     */
    public boolean registerUser(String userLogin, String userPassword, String userName, String userNameRU) {

        if (getUser(userLogin) != null) {
            return false;
        }

        try (var preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {

            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, userPassword);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, userNameRU);

            if (1 == preparedStatement.executeUpdate()) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        // TODO: since this method has nothing common with database interactions, consider moving it somewhere outside (perhaps it makes sense to create a special utility class for this purpose)

        var users = getAllUsers();

        List<User> usersOnPage = new ArrayList<>();

        for (int i = offset; i < offset + recordsPerPage; i++) {

            if (i < users.size()) {
                usersOnPage.add(users.get(i));
            }
        }

        return usersOnPage;
    }

    /**
     * Returns all users.
     *
     * @return List of user entities.
     */
    public List<User> getAllUsers() {

        List<User> users;

        try (var preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS);
             var resultSet = preparedStatement.executeQuery()
        ) {
            users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), "ROLE_USER", resultSet.getString(4), resultSet.getString(5)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        try (var preparedStatement = connection.prepareStatement(SQL_GET_USER)) {

            preparedStatement.setString(1, userLogin);

            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        try (var preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BLOCK_STATUS)) {

            preparedStatement.setString(1, blockStatus);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}