package database.dao;

import java.util.*;
import java.sql.*;

import database.DBConnectionUtil;
import entity.Category;

/**
 * Data accessor object for the category entity.
 *
 * @author Vlad Pasichniuk
 *
 */

public class CategoryDAO {

    private static final String SQL_GET_ALL_CATEGORIES = "SELECT * FROM online_store.category";
    private static final String SQL_GET_CATEGORY_ID = "SELECT category_id FROM online_store.category WHERE category_name=?";
    private static final String SQL_GET_CATEGORY_RU_ID = "SELECT category_id FROM online_store.category WHERE category_name_ru=?";
    private static final String SQL_INSERT_CATEGORY = "INSERT INTO online_store.category (category_name, category_name_ru) VALUES (?, ?)";

    private final Connection connection;

    public CategoryDAO() {
        this.connection = DBConnectionUtil.getInstance().getConnection();
    }

    /**
     * Returns all categories.
     *
     * @return List of category entities.
     */
    public List<Category> getAllCategories() {

        List<Category> categories;

        try (var preparedStatement = connection.prepareStatement(SQL_GET_ALL_CATEGORIES);
             var resultSet = preparedStatement.executeQuery()
        ) {
            categories = new ArrayList<>();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return List.copyOf(categories);
    }

    /**
     * Returns category identifier by category name.
     *
     * @param category Name of category.
     *
     * @return Category identifier.
     */
    public int getCategoryID(String category) {

        // TODO: split this method into 2 parts im order to reduce its verbosity.

        try (var preparedStatement = connection.prepareStatement(SQL_GET_CATEGORY_ID);
             var preparedStatementRu = connection.prepareStatement(SQL_GET_CATEGORY_RU_ID)
        ) {

            preparedStatement.setString(1, category);

            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }

            preparedStatementRu.setString(1, category);

            try (var resultSetRu = preparedStatementRu.executeQuery()) {

                if (resultSetRu.next()) {
                    return resultSetRu.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    /**
     * Inserts a new category.
     *
     * @param name Category name.
     * @param nameRU Category name in russian.
     */
    public void insertCategory(String name, String nameRU) {

        try (var preparedStatement = connection.prepareStatement(SQL_INSERT_CATEGORY)) {

            if (getCategoryID(name) != 0 || getCategoryID(nameRU) != 0) {
                return;
            }

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, nameRU);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}