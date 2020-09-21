package dao;

import util.DBConnectionUtil;
import java.sql.*;

public class UserDAO {

    private static final String SQL_GET_USER = "SELECT user_login FROM online_store.store_user " +
            "WHERE user_login=? AND user_password=aes_encrypt(?, 'password')";

    private static final String SQL_INSERT_USER = "INSERT INTO online_store.store_user(user_login, user_role, user_password) " +
            "VALUES(?, 1, aes_encrypt(?, 'password'))";

    private Connection connection;

    public boolean authorizeUser(String userLogin, String userPassword) {
        try {
            connection = DBConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement(SQL_GET_USER);
            ps.setString(1, userLogin);
            ps.setString(2, userPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return true;

        } catch (SQLException exception) {
            return false;
        }

        return false;
    }

    public boolean registerUser(String userLogin, String userPassword) {
        try {
            connection = DBConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER);
            ps.setString(1, userLogin);
            ps.setString(2, userPassword);

            if (ps.executeUpdate() == 1)
                return true;

        } catch (SQLException exception) {
            return false;
        }

        return false;
    }
}
