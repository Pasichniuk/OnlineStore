package database.dao;

import entity.Product;
import database.DBConnectionUtil;

import java.util.*;
import java.sql.*;

public class ProductDAO {

    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT product_id, product_name, category_name, category_name_ru, price, addition_date FROM online_store.product \n" +
            "JOIN online_store.category ON product.category = category.category_id WHERE price BETWEEN ? AND ?";

    private static final String SQL_GET_PRODUCT = "SELECT product_id, product_name, category_name, category_name_ru, price, addition_date FROM online_store.product \n" +
            "JOIN online_store.category ON product.category = category.category_id WHERE product_id=?";

    private static final String SQL_DELETE_PRODUCT = "DELETE FROM online_store.product WHERE product_id=?";

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO online_store.product (product_name, category, price) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE_PRODUCT = "UPDATE online_store.product SET product_name=?, category=?, price=? WHERE product_id=?";

    private static final String SQL_GET_PRODUCT_ID = "SELECT product_id FROM online_store.product WHERE product_name=?";

    private static final String SQL_GET_CATEGORY_ID = "SELECT category_id FROM online_store.category WHERE category_name=?";

    private static final String SQL_GET_CATEGORY_RU_ID = "SELECT category_id FROM online_store.category WHERE category_name_ru=?";

    private final Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ProductDAO() {
        connection = DBConnectionUtil.getConnection();
    }

    public List<Product> getProductsOnPage(int offset, int recordsPerPage, List<Product> products) {
        List<Product> productsOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + recordsPerPage); i++) {

            if (i < products.size())
                productsOnPage.add(products.get(i));
        }

        return productsOnPage;
    }

    public List<Product> getAllProducts(int minPrice, int maxPrice) {
        List<Product> products = null;
        Product product;

        try {
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS);
            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);

            resultSet = preparedStatement.executeQuery();

            products = new ArrayList<>();

            while (resultSet.next()) {
                product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(5), resultSet.getDate(6));
                product.setCategoryRU(resultSet.getString(4));
                products.add(product);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return products;
    }

    public Product getProduct(int productID) {
        Product product = null;

        try {
            preparedStatement = connection.prepareStatement(SQL_GET_PRODUCT);
            preparedStatement.setInt(1, productID);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(5), resultSet.getDate(6));
                product.setCategoryRU(resultSet.getString(4));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return product;
    }

    public void deleteProduct(int productID) {
        try {
            preparedStatement = connection.prepareStatement(SQL_DELETE_PRODUCT);
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insertProduct(String productName, String category, float price) {
        try {
            int categoryID = getCategoryID(category);

            if (categoryID == 0 || productExists(productName))
                return;

            preparedStatement = connection.prepareStatement(SQL_INSERT_PRODUCT);
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, categoryID);
            preparedStatement.setFloat(3, price);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateProduct(int productID, String productName, String category, float price) {
        try {
            int categoryID = getCategoryID(category);

            if (categoryID == 0)
                return;

            preparedStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT);
            preparedStatement.setString(1, productName);
            preparedStatement.setInt(2, categoryID);
            preparedStatement.setFloat(3, price);
            preparedStatement.setInt(4, productID);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private boolean productExists(String productName) {
        try {
            preparedStatement = connection.prepareStatement(SQL_GET_PRODUCT_ID);
            preparedStatement.setString(1, productName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return true;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    private int getCategoryID(String category) {
        try {
            preparedStatement = connection.prepareStatement(SQL_GET_CATEGORY_ID);
            preparedStatement.setString(1, category);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt(1);

            preparedStatement = connection.prepareStatement(SQL_GET_CATEGORY_RU_ID);
            preparedStatement.setString(1, category);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }
}