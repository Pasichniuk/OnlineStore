package dao;

import entity.Order;
import entity.Product;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final String SQL_GET_USER_ORDERS = "SELECT * FROM online_store.order WHERE user_id=?";

    private static final String SQL_INSERT_ORDER = "INSERT INTO `online_store`.`order` (`user_id`) VALUES (?)";

    private static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO `online_store`.`order_product` VALUES (?, ?)";

    private static final String SQL_GET_ORDER_ID = "SELECT order_id FROM online_store.order WHERE user_id=?";

    private Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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

            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER);
            preparedStatement.setInt(1, userID);

            if (preparedStatement.executeUpdate() == 1) {
                int orderID = getOrderID(userID);

                for (Product product : products)
                    insertOrderProduct(orderID, product.getId());
            }

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

    private int getOrderID(int userID) {
        try {
            connection = DBConnectionUtil.getConnection();

            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_ID);
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }
}
