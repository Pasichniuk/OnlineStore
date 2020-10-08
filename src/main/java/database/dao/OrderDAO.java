package database.dao;

import entity.Order;
import entity.Product;
import database.DBConnectionUtil;

import org.apache.log4j.Logger;
import java.util.*;
import java.sql.*;

/**
 * Data accessor object for Order entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class OrderDAO {

    private static final Logger logger = Logger.getLogger(OrderDAO.class);

    private static final String SQL_FIND_ALL_ORDERS = "SELECT order_id, online_store.order.user_id, order_status, user_login FROM online_store.order " +
            "JOIN online_store.store_user ON online_store.order.user_id = store_user.user_id";

    private static final String SQL_GET_USER_ORDERS = "SELECT * FROM online_store.order WHERE user_id=?";

    private static final String SQL_INSERT_ORDER = "INSERT INTO online_store.order (user_id) VALUES (?)";

    private static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO online_store.order_product VALUES (?, ?, ?)";

    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE online_store.order SET order_status=? WHERE order_id=? AND order_status='REGISTERED'";

    private static final String SQL_GET_ORDER_PRODUCTS = "SELECT * FROM online_store.order_product WHERE order_id=?";

    private final Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public OrderDAO() {
        connection = DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Returns orders on page.
     *
     * @param offset Offset.
     * @param recordsPerPage Amount of records on page.
     *
     * @return List of order entities.
     */
    public List<Order> getOrdersOnPage(int offset, int recordsPerPage) {
        List<Order> orders = getAllOrders();
        List<Order> ordersOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + recordsPerPage); i++) {

            if (i < orders.size())
                ordersOnPage.add(orders.get(i));
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
        Order order;

        try {
            Statement st = connection.createStatement();

            resultSet = st.executeQuery(SQL_FIND_ALL_ORDERS);

            orders = new ArrayList<>();

            while (resultSet.next()) {
                order = new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
                order.setUserLogin(resultSet.getString(4));
                orders.add(order);
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
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
        Order order;

        try {
            preparedStatement = connection.prepareStatement(SQL_GET_USER_ORDERS);
            preparedStatement.setInt(1, userID);

            resultSet = preparedStatement.executeQuery();

            orders = new ArrayList<>();

            while (resultSet.next()) {
                order = new Order(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
                orders.add(order);
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
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
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userID);

            if (preparedStatement.executeUpdate() != 1)
                return;

            resultSet = preparedStatement.getGeneratedKeys();

            int orderID = 0;

            if (resultSet.next())
                orderID = resultSet.getInt(1);

            for (Product product : products)
                insertOrderProduct(orderID, product.getId(), product.getCount());

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
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
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER_PRODUCT);
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, productID);
            preparedStatement.setInt(3, productCount);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Updates status of order.
     *
     * @param orderID Order identifier.
     * @param status Status.
     */
    public void updateOrderStatus(int orderID, String status) {
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);

            if (preparedStatement.executeUpdate() == 1)
                new ProductDAO().operateProductsAmount(orderID, status);

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
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
        Product product;

        try {
            preparedStatement = connection.prepareStatement(SQL_GET_ORDER_PRODUCTS);
            preparedStatement.setInt(1, orderID);

            resultSet = preparedStatement.executeQuery();

            orderProducts = new ArrayList<>();

            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt(2));
                product.setCount(resultSet.getInt(3));
                orderProducts.add(product);
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
        }

        return orderProducts;
    }
}
