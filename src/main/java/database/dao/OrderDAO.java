package database.dao;

import java.util.*;
import java.sql.*;

import database.DBConnectionUtil;
import entity.Order;
import entity.Product;

/**
 * Data accessor object for the order entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class OrderDAO {

    private static final String SQL_FIND_ALL_ORDERS = "SELECT order_id, online_store.order.user_id, order_status, user_login FROM online_store.order JOIN online_store.store_user ON online_store.order.user_id = store_user.user_id";
    private static final String SQL_GET_USER_ORDERS = "SELECT * FROM online_store.order WHERE user_id=?";
    private static final String SQL_INSERT_ORDER = "INSERT INTO online_store.order (user_id) VALUES (?)";
    private static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO online_store.order_product VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE online_store.order SET order_status=? WHERE order_id=? AND order_status='REGISTERED'";
    private static final String SQL_GET_ORDER_PRODUCTS = "SELECT * FROM online_store.order_product WHERE order_id=?";

    private final Connection connection;

    public OrderDAO() {
        this.connection = DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Returns the orders on page.
     *
     * @param offset Offset.
     * @param recordsPerPage Amount of records on page.
     *
     * @return List of order entities.
     */
    public List<Order> getOrdersOnPage(int offset, int recordsPerPage) {

        // TODO: since this method has nothing common with database interactions, consider moving it somewhere outside (perhaps it makes sense to create a special utility class for this purpose)

        var orders = getAllOrders();
        List<Order> ordersOnPage = new ArrayList<>();

        for (int i = offset; i < offset + recordsPerPage; i++) {

            if (i < orders.size()) {
                ordersOnPage.add(orders.get(i));
            }
        }

        return ordersOnPage;
    }

    /**
     * Returns all orders.
     *
     * @return List of order entities.
     */
    public List<Order> getAllOrders() {

        List<Order> orders;

        try (var preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS);
             var resultSet = preparedStatement.executeQuery()
        ) {

            orders = new ArrayList<>();

            while (resultSet.next()) {
                var order = new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
                order.setUserLogin(resultSet.getString(4));
                orders.add(order);
            }

            orders.sort(Comparator.comparing(Order::getStatus).reversed());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    /**
     * Returns orders of specific User.
     *
     * @param userID User identifier.
     *
     * @return List of order entities.
     */
    public List<Order> getUserOrders(int userID) {

        List<Order> orders;

        try (var preparedStatement = connection.prepareStatement(SQL_GET_USER_ORDERS)) {

            preparedStatement.setInt(1, userID);

            try (var resultSet = preparedStatement.executeQuery()) {

                orders = new ArrayList<>();

                while (resultSet.next()) {
                    orders.add(new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3)));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    /**
     * Inserts order.
     *
     * @param userID User identifier.
     * @param products List of products in Order.
     */
    public void insertOrder(int userID, List<Product> products) {

        try (var preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, userID);

            if (preparedStatement.executeUpdate() != 1) {
                return;
            }

            try (var resultSet = preparedStatement.getGeneratedKeys()) {

                var orderID = 0;

                if (resultSet.next()) {
                    orderID = resultSet.getInt(1);
                }

                for (var product : products) {
                    insertOrderProduct(orderID, product.getId(), product.getCount());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts products from order.
     *
     * @param orderID Order identifier.
     * @param productID Product identifier.
     * @param productCount Product count.
     */
    private void insertOrderProduct(int orderID, int productID, int productCount) {

        try (var preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER_PRODUCT)) {

            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, productID);
            preparedStatement.setInt(3, productCount);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the order status.
     *
     * @param orderID Order identifier.
     * @param status Status.
     */
    public void updateOrderStatus(int orderID, String status) {

        try (var preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);

            if (1 == preparedStatement.executeUpdate()) {
                new ProductDAO().operateProductsAmount(orderID, status);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns list of products from specific Order.
     *
     * @param orderID Order identifier.
     *
     * @return List of product entities.
     */
    public List<Product> getOrderProducts(int orderID) {

        List<Product> orderProducts;

        try (var preparedStatement = connection.prepareStatement(SQL_GET_ORDER_PRODUCTS)) {

            preparedStatement.setInt(1, orderID);

            try (var resultSet = preparedStatement.executeQuery()) {

                orderProducts = new ArrayList<>();

                while (resultSet.next()) {
                    var product = new Product();
                    product.setId(resultSet.getInt(2));
                    product.setCount(resultSet.getInt(3));
                    orderProducts.add(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderProducts;
    }
}