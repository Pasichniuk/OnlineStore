package controller.admin;

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

@WebServlet(name = "AdminProductsServlet", urlPatterns = "/admin-catalog")
public class AdminProductsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminProductsServlet.class);

    private static final String CHECK_INPUT_REGEX = "^[a-zA-Z0-9 ._-]{3,}$";

    private static final int MAX_PRICE = 10_000;
    private static final int MIN_PRICE = 0;

    private static final int RECORDS_PER_PAGE = 5;
    private int productsAmount;
    private int pageNumber = 1;
    private int pagesAmount;

    private final ProductDAO productDAO;

    private List<Product> products;

    public AdminProductsServlet() {
        productDAO = new ProductDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action.equals("ADD")) {
            addProduct(request, response);

        } else if (action.equals("EDIT")) {
            editProduct(request, response);

        } else
            response.sendRedirect("/admin-catalog");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteProduct(request, response))
            return;

        request.getSession().setAttribute("categories", productDAO.getAllCategories());

        products = productDAO.getAllProducts(MIN_PRICE, MAX_PRICE);
        productsAmount = products.size();

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsOnPage();

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("/view/admin/admin-catalog-jsp.jsp").forward(request, response);
    }

    private void getProductsOnPage() {
        products = productDAO.getProductsOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE, products);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / RECORDS_PER_PAGE);
    }

    /**
     * Validates input and adds new product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productName = request.getParameter("productName");
        String category = new String(request.getParameter("category").getBytes(StandardCharsets.ISO_8859_1), "cp1251");
        String price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.insertProduct(productName, category, Float.parseFloat(price));
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' added new product...");
            response.sendRedirect("/admin-catalog");
        } else
            response.getWriter().write(notifyIncorrectInput());
    }

    /**
     * Validates input and edits product.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If redirect failed.
     */
    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String category = new String(request.getParameter("category").getBytes(StandardCharsets.ISO_8859_1), "cp1251");
        String price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.updateProduct(productID, productName, category, Float.parseFloat(price));
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' edited product with ID=" + productID + "...");
            response.sendRedirect("/admin-catalog");
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
            response.sendRedirect("/admin-catalog");
            return true;
        }

        return false;
    }

    private String notifyIncorrectInput() {
        return "<script>" + "alert('Incorrect input! Please, try again.');" + "window.location = 'http://localhost:8080/admin-catalog';" + "</script>";
    }
}
