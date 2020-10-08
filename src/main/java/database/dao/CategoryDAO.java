package database.dao;

import database.DBConnectionUtil;
import entity.Category;

import org.apache.log4j.Logger;
import java.util.*;
import java.sql.*;

/**
 * Data accessor object for Category entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class CategoryDAO {

    private static final Logger logger = Logger.getLogger(CategoryDAO.class);

    private static final String SQL_GET_ALL_CATEGORIES = "SELECT * FROM online_store.category";

    private static final String SQL_GET_CATEGORY_ID = "SELECT category_id FROM online_store.category WHERE category_name=?";

    private static final String SQL_GET_CATEGORY_RU_ID = "SELECT category_id FROM online_store.category WHERE category_name_ru=?";

    private static final String SQL_INSERT_CATEGORY = "INSERT INTO online_store.category (category_name, category_name_ru) VALUES (?, ?)";

    private final Connection connection;

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CategoryDAO() {
        connection = DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Returns all categories.
     *
     * @return List of category entities.
     */
    public List<Category> getAllCategories() {
        List<Category> categories;
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
            logger.error(exception.getMessage());
            throw new RuntimeException();
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
            logger.error(exception.getMessage());
            throw new RuntimeException();
        }

        return 0;
    }

    /**
     * Inserts new category.
     *
     * @param name Category name.
     * @param nameRU Category name in russian.
     */
    public void insertCategory(String name, String nameRU) {
        try {
            if (getCategoryID(name) != 0 || getCategoryID(nameRU) != 0)
                return;

            preparedStatement = connection.prepareStatement(SQL_INSERT_CATEGORY);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, nameRU);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException();
        }
    }
}
