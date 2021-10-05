package database.dao;

import java.util.*;
import java.sql.*;

import database.DBConnectionUtil;
import entity.Product;

/**
 * Data accessor object for Product & Category entities.
 *
 * @author Vlad Pasichniuk
 *
 */

public class ProductDAO {

    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT product_id, product_name, category_name, category_name_ru, price, addition_date, count, reserve FROM online_store.product JOIN online_store.category ON product.category = category.category_id WHERE price BETWEEN ? AND ?";
    private static final String SQL_GET_PRODUCT = "SELECT product_id, product_name, category_name, category_name_ru, price, addition_date FROM online_store.product JOIN online_store.category ON product.category = category.category_id WHERE product_id=?";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM online_store.product WHERE product_id=?";
    private static final String SQL_INSERT_PRODUCT = "INSERT INTO online_store.product (product_name, category, price, count, reserve) VALUES (?, ?, ?, ?, 0)";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE online_store.product SET product_name=?, category=?, price=? WHERE product_id=?";
    private static final String SQL_GET_PRODUCT_ID = "SELECT product_id FROM online_store.product WHERE product_name=?";
    private static final String SQL_SET_PRODUCT_RESERVE = "UPDATE online_store.product SET reserve=(reserve + ?) WHERE product_id=?";
    private static final String SQL_PRODUCT_BOUGHT = "UPDATE online_store.product SET count=(count - reserve), reserve =(reserve - ?) WHERE product_id=?";
    private static final String SQL_PRODUCT_CANCEL_RESERVE = "UPDATE online_store.product SET reserve =(reserve - ?) WHERE product_id=?";

    private final Connection connection;

    public ProductDAO() {
        this.connection = DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Returns products on page.
     *
     * @param offset Offset.
     * @param recordsPerPage Amount of records on page.
     *
     * @return List of product entities.
     */
    public List<Product> getProductsOnPage(
        int offset,
        int recordsPerPage,
        int minPrice,
        int maxPrice
    ) {
        // TODO: since this method has nothing common with database interactions, consider moving it somewhere outside (perhaps it makes sense to create a special utility class for this purpose)

        List<Product> productsOnPage = new ArrayList<>();

        var products = getAllProducts(minPrice, maxPrice);

        for (int i = offset; i < offset + recordsPerPage; i++) {

            if (i < products.size()) {
                productsOnPage.add(products.get(i));
            }
        }

        return productsOnPage;
    }

    /**
     * Returns all products.
     *
     * @param minPrice Minimal price.
     * @param maxPrice Maximal price.
     *
     * @return List of protuct entities.
     */
    public List<Product> getAllProducts(int minPrice, int maxPrice) {

        List<Product> products;

        try (var preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS)) {

            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);

            try (var resultSet = preparedStatement.executeQuery()) {
                products = new ArrayList<>();

                while (resultSet.next()) {

                    var product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(5), resultSet.getDate(6));

                    product.setCategoryRU(resultSet.getString(4));
                    product.setCount(resultSet.getInt(7));
                    product.setReserve(resultSet.getInt(8));

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    /**
     * Returns Product by ID.
     *
     * @param productID Product identifier.
     *
     * @return Product entity.
     */
    public Product getProduct(int productID) {

        Product product = null;

        try (var preparedStatement = connection.prepareStatement(SQL_GET_PRODUCT)) {

            preparedStatement.setInt(1, productID);

            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(5), resultSet.getDate(6));
                    product.setCategoryRU(resultSet.getString(4));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    /**
     * Deletes product by ID.
     *
     * @param productID Product identifier.
     */
    public void deleteProduct(int productID) {

        try (var preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT)) {

            preparedStatement.setInt(1, productID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts a new product.
     *
     * @param productName Name of product.
     * @param category Category of product.
     * @param price Price of product.
     */
    public void insertProduct(String productName, String category, double price, int count) {

        var categoryID = new CategoryDAO().getCategoryID(category);

        if (categoryID == 0 || productExists(productName)) {
            return;
        }

        try (var preparedStatement = connection.prepareStatement(SQL_INSERT_PRODUCT)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, categoryID);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, count);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates product info.
     *
     * @param productID Product identifier.
     * @param productName Name of product.
     * @param category Category of product.
     * @param price Price of product.
     */
    public void updateProduct(int productID, String productName, String category, double price) {

        var categoryID = new CategoryDAO().getCategoryID(category);

        if (categoryID == 0) {
            return;
        }

        // TODO: extract some common SQL logic into a common method in order to reduct the amount of boilerplate code

        try (var preparedStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT)) {

            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, categoryID);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, productID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates reserve of product.
     *
     * @param productID Product identifier.
     * @param reserve Reserve amount.
     */
    public void setProductReserve(int productID, int reserve) {

        try (var preparedStatement = connection.prepareStatement(SQL_SET_PRODUCT_RESERVE)) {

            preparedStatement.setInt(1, reserve);
            preparedStatement.setInt(2, productID);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes products reserve/count.
     *
     * @param orderID Order identifier.
     * @param status Order status.
     */
    public void operateProductsAmount(int orderID, String status) {

        var orderProducts = new OrderDAO().getOrderProducts(orderID);

        String query = null;

        if (status.equals("PAID")) {
            query = SQL_PRODUCT_BOUGHT;
        } else if (status.equals("CANCELLED")) {
            query = SQL_PRODUCT_CANCEL_RESERVE;
        }

        try (var preparedStatement = connection.prepareStatement(query)) {

            for (var product : orderProducts) {

                preparedStatement.setInt(1, product.getCount());
                preparedStatement.setInt(2, product.getId());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the product already exists.
     *
     * @param productName Name of product.
     *
     * @return If product already exists.
     */
    private boolean productExists(String productName) {

        try (var preparedStatement = connection.prepareStatement(SQL_GET_PRODUCT_ID)) {

            preparedStatement.setString(1, productName);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}