package controller.admin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.CategoryDAO;
import database.dao.ProductDAO;
import constant.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Admin product controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminProductsServlet", urlPatterns = Constants.PATH_ADMIN_CATALOG)
public class AdminProductsServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminProductsServlet.class);
    private static final String CHECK_INPUT_REGEX = "^[a-zA-Z0-9 ._-]{3,}$";
    private static final String CHECK_NAME_REGEX = "^[a-zA-Z\\s-]{3,}$";
    private static final String CHECK_NAME_RU_REGEX = "^[а-яА-ЯёЁ\\s-]{3,}$";

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    public AdminProductsServlet() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var action = request.getParameter("action");

        switch (action) {

            case "ADD":
                addProduct(request, response);
                break;
            case "EDIT":
                editProduct(request, response);
                break;
            case "ADD_CATEGORY":
                addCategory(request, response);
                break;
            default:
                break;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (deleteProduct(request, response)) {
            return;
        }

        request.getSession().setAttribute("categories", categoryDAO.getAllCategories());

        var pageNumber = 1;

        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        var products = productDAO.getProductsOnPage(
            (pageNumber - 1) * Constants.RECORDS_PER_PAGE,
            Constants.RECORDS_PER_PAGE,
            Constants.MIN_PRICE,
            Constants.MAX_PRICE
        );

        var pageAmount = (int) Math.ceil(products.size() * 1.0 / Constants.RECORDS_PER_PAGE);

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pageAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_ADMIN_CATALOG_JSP).forward(request, response);
    }

    /**
     * Adds new product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var productName = request.getParameter("productName");
        var category = request.getParameter("category");
        var price = request.getParameter("price");
        var count = request.getParameter("count");

        if (productName.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.insertProduct(productName, category, Double.parseDouble(price), Integer.parseInt(count));
            LOGGER.info("Admin '{}' added new product", request.getSession().getAttribute("userLogin"));
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
        } else {
            response.getWriter().write(notifyIncorrectInput());
        }
    }

    /**
     * Edits a product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var productID = Integer.parseInt(request.getParameter("productID"));
        var productName = request.getParameter("productName");
        var category = request.getParameter("category");
        var price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.updateProduct(productID, productName, category, Double.parseDouble(price));
            LOGGER.info("Admin '{}' edited product with ID={}", request.getSession().getAttribute("userLogin"), productID);
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
        } else {
            response.getWriter().write(notifyIncorrectInput());
        }
    }

    /**
     * Deletes a product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether product is deleted.
     *
     * @throws IOException If redirect failed.
     */
    private boolean deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var productID = request.getParameter("productID");

        if (productID != null) {
            productDAO.deleteProduct(Integer.parseInt(productID));
            LOGGER.info("Admin '{}' deleted product with ID={}", request.getSession().getAttribute("userLogin"), productID);
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
            return true;
        }

        return false;
    }

    /**
     * Adds a new category.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var categoryName = request.getParameter("categoryName");
        var categoryNameRU = new String(request.getParameter("categoryNameRU").getBytes(StandardCharsets.ISO_8859_1), "cp1251");

        if (categoryName.matches(CHECK_NAME_REGEX) && categoryNameRU.matches(CHECK_NAME_RU_REGEX)) {
            categoryDAO.insertCategory(categoryName, categoryNameRU);
            LOGGER.info("Admin '{}' added new category", request.getSession().getAttribute("userLogin"));
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
        } else {
            response.getWriter().write(notifyIncorrectInput());
        }
    }

    private String notifyIncorrectInput() {
        return "<script>alert('Incorrect input! Please, try again.');window.location = 'http://localhost:8080/admin-catalog';</script>";
    }
}