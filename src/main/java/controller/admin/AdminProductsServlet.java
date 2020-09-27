package controller.admin;

import dao.ProductDAO;
import entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductsServlet", urlPatterns = "/admin-catalog")
public class AdminProductsServlet extends HttpServlet {

    private static final String CHECK_INPUT_REGEX = "^[a-zA-Z0-9 ._-]{3,}$";

    private static final int MAX_PRICE = 10_000;
    private static final int MIN_PRICE = 0;

    private static final int RECORDS_PER_PAGE = 5;
    private final int productsAmount;
    private int pageNumber = 1;
    private int pagesAmount;

    private final ProductDAO productDAO;

    private List<Product> products;

    public AdminProductsServlet() {
        productDAO = new ProductDAO();
        products = productDAO.getAllProducts(MIN_PRICE, MAX_PRICE);
        productsAmount = products.size();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action.equals("ADD")) {
            addProduct(request, response);

        } else if (action.equals("EDIT")) {
            editProduct(request, response);

        } else
            response.sendRedirect("/view/admin/product-add-jsp.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteProduct(request, response))
            return;

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsOnPage();

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("/view/admin/admin-catalog-jsp.jsp").forward(request, response);
    }

    private void getProductsOnPage() {
        products = productDAO.getProductsOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE, MIN_PRICE, MAX_PRICE);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / RECORDS_PER_PAGE);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productName = request.getParameter("productName");
        String category = request.getParameter("category");
        String price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && category.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.insertProduct(productName, category, Float.parseFloat(price));
            response.sendRedirect("/admin-catalog");
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        String category = request.getParameter("category");
        String price = request.getParameter("price");

        if (productName.matches(CHECK_INPUT_REGEX) && category.matches(CHECK_INPUT_REGEX) && price != null) {
            productDAO.updateProduct(productID, productName, category, Float.parseFloat(price));
            response.sendRedirect("/admin-catalog");
        }
    }

    private boolean deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("productID");

        if (productID != null) {
            productDAO.deleteProduct(Integer.parseInt(productID));
            response.sendRedirect("/admin-catalog");
            return true;
        }

        return false;
    }
}
