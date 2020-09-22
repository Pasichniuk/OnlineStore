package dao;

import entity.Product;
import util.DBConnectionUtil;

import java.util.*;
import java.sql.*;

public class ProductDAO {

    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT product_id, product_name, category_name, price, addition_date FROM online_store.product \n" +
            "JOIN online_store.category ON product.category = category.category_id";

    private static final String SQL_GET_PRODUCT = "SELECT product_id, product_name, category_name, price, addition_date FROM online_store.product \n" +
            "JOIN online_store.category ON product.category = category.category_id WHERE product_id=?";

    private Connection connection;
    private ResultSet resultSet;

    public List<Product> getAllProducts() {
        List<Product> products = null;
        Product product;

        try {
            connection = DBConnectionUtil.getConnection();

            Statement st = connection.createStatement();

            resultSet = st.executeQuery(SQL_FIND_ALL_PRODUCTS);

            products = new ArrayList<>();

            while (resultSet.next()) {
                product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getDate(5));
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
            connection = DBConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement(SQL_GET_PRODUCT);
            ps.setInt(1, productID);

            resultSet = ps.executeQuery();

            if (resultSet.next())
                product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getDate(5));

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return product;
    }
}
