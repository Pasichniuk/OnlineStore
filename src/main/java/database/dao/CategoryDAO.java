package database.dao;

import database.DBConnectionUtil;
import entity.Category;

import java.util.*;
import java.sql.*;

public class CategoryDAO {

    private static final String SQL_GET_ALL_CATEGORIES = "SELECT * FROM online_store.category";

    private static final String SQL_GET_CATEGORY_ID = "SELECT category_id FROM online_store.category WHERE category_name=?";

    private static final String SQL_GET_CATEGORY_RU_ID = "SELECT category_id FROM online_store.category WHERE category_name_ru=?";

    private final Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CategoryDAO() {
        connection = DBConnectionUtil.getConnection();
    }

    /**
     * Returns all categories.
     *
     * @return List of category entities.
     */
    public List<Category> getAllCategories() {
        List<Category> categories = null;
        Category category;

        try {
            preparedStatement = connection.prepareStatement(SQL_GET_ALL_CATEGORIES);
            resultSet = preparedStatement.executeQuery();

            categories = new ArrayList<>();

            while (resultSet.next()) {
                category = new Category(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                categories.add(category);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return categories;
    }

    /**
     * Returns category identifier by category name.
     *
     * @param category Name of category.
     *
     * @return Category identifier.
     */
    public int getCategoryID(String category) {
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
