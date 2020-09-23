package dao;

import entity.Order;
import entity.Product;
import util.DBConnectionUtil;

import java.util.*;
import java.sql.*;

public class OrderDAO {

    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM online_store.order";

    private static final String SQL_GET_USER_ORDERS = "SELECT * FROM online_store.order WHERE user_id=?";

    private static final String SQL_INSERT_ORDER = "INSERT INTO online_store.order (user_id) VALUES (?)";

    private static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO online_store.order_product VALUES (?, ?)";

    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE online_store.order SET order_status=? WHERE order_id=? AND order_status='REGISTERED'";

    private Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public List<Order> getAllOrders() {
        List<Order> orders = null;
        Order order;

        try {
            connection = DBConnectionUtil.getConnection();

            Statement st = connection.createStatement();

            resultSet = st.executeQuery(SQL_FIND_ALL_ORDERS);

            orders = new ArrayList<>();

            while (resultSet.next()) {
                order = new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
                orders.add(order);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return orders;
    }

    public List<Order> getUserOrders(int userID) {
        List<Order> orders = null;
        Order order;

        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_GET_USER_ORDERS);
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            orders = new ArrayList<>();

            while (resultSet.next()) {
                order = new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
                orders.add(order);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return orders;
    }

    public void insertOrder(int userID, List<Product> products) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userID);

            if (preparedStatement.executeUpdate() != 1)
                return;

            resultSet = preparedStatement.getGeneratedKeys();

            int orderID = 0;

            if (resultSet.next())
                orderID = resultSet.getInt(1);

            for (Product product : products)
                insertOrderProduct(orderID, product.getId());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void insertOrderProduct(int orderID, int productID) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER_PRODUCT);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, productID);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateOrderStatus(int orderID, String status) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
