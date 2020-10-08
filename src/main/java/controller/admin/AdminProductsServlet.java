package controller.admin;

import constant.Constants;
import database.dao.CategoryDAO;
import database.dao.ProductDAO;
import entity.Product;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Admin Products servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminProductsServlet", urlPatterns = Constants.PATH_ADMIN_CATALOG)
public class AdminProductsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminProductsServlet.class);

    private static final String CHECK_INPUT_REGEX = "^[a-zA-Z0-9 ._-]{3,}$";
    private static final String CHECK_NAME_REGEX = "^[a-zA-Z\\s-]{3,}$";
    private static final String CHECK_NAME_RU_REGEX = "^[а-яА-ЯёЁ\\s-]{3,}$";

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private List<Product> products;

    private int pageNumber = 1;

    private int productsAmount;
    private int pagesAmount;

    public AdminProductsServlet() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

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
        if (deleteProduct(request, response))
            return;

        request.getSession().setAttribute("categories", categoryDAO.getAllCategories());

        products = productDAO.getAllProducts(Constants.MIN_PRICE, Constants.MAX_PRICE);

        productsAmount = products.size();

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsOnPage();

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_ADMIN_CATALOG_JSP).forward(request, response);
    }

    private void getProductsOnPage() {
        products = productDAO.getProductsOnPage((pageNumber-1) * Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE, products);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / Constants.RECORDS_PER_PAGE);
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
        String productName = request.getParameter("productName");
        String category = request.getParameter("category");
        String price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.insertProduct(productName, category, Float.parseFloat(price));
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' added new product...");
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
        } else
            response.getWriter().write(notifyIncorrectInput());
    }

    /**
     * Edits product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String category = request.getParameter("category");
        String price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.updateProduct(productID, productName, category, Float.parseFloat(price));
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' edited product with ID=" + productID + "...");
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
        } else
            response.getWriter().write(notifyIncorrectInput());
    }

    /**
     * Deletes product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether product is deleted.
     *
     * @throws IOException If redirect failed.
     */
    private boolean deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("productID");

        if (productID != null) {
            productDAO.deleteProduct(Integer.parseInt(productID));
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' deleted product with ID=" + productID + "...");
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
            return true;
        }

        return false;
    }

    /**
     * Adds new category.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String categoryName = request.getParameter("categoryName");
        String categoryNameRU = new String(request.getParameter("categoryNameRU").getBytes(StandardCharsets.ISO_8859_1), "cp1251");

        if (categoryName.matches(CHECK_NAME_REGEX) && categoryNameRU.matches(CHECK_NAME_RU_REGEX)) {
            categoryDAO.insertCategory(categoryName, categoryNameRU);
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' added new category...");
            response.sendRedirect(Constants.PATH_ADMIN_CATALOG);
        } else
            response.getWriter().write(notifyIncorrectInput());
    }

    private String notifyIncorrectInput() {
        return "<script>" + "alert('Incorrect input! Please, try again.');" + "window.location = 'http://localhost:8080/admin-catalog';" + "</script>";
    }
}
