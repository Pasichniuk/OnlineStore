package controller.user;

import database.dao.CategoryDAO;
import database.dao.ProductDAO;
import entity.Product;
import util.Sorter;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Product servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "ProductServlet", urlPatterns = "/catalog")
public class ProductServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProductServlet.class);

    private static final int CART_LIMIT = 20;

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private List<Product> products;

    private String selectedCategory;
    private String sortingOption;

    private int minPrice = 0;
    private int maxPrice = 10_000;

    private static final int RECORDS_PER_PAGE = 5;
    private int productsAmount;
    private int pageNumber = 1;
    private int pagesAmount;

    public ProductServlet() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        products = productDAO.getAllProducts(minPrice, maxPrice);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        addProductToCart(request);
        response.sendRedirect("/catalog");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("categories", categoryDAO.getAllCategories());

        productsAmount = products.size();

        getProductsFromPriceRange(request);

        if (request.getParameter("Category") != null)
            selectedCategory = request.getParameter("Category");

        getProductsFromCategory();

        if (request.getParameter("Sort") != null)
            sortingOption = request.getParameter("Sort");

        sortProducts(sortingOption);

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsOnPage();

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("view/user/catalog-jsp.jsp").forward(request, response);
    }

    /**
     * Adds product to cart.
     *
     * @param request Request.
     */
    private void addProductToCart(HttpServletRequest request) {
        String productID = request.getParameter("ProductID");

        HttpSession session = request.getSession();

        List<Product> cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (productID != null) {

            if (cartProducts != null) {

                if (cartProducts.size() < CART_LIMIT) {
                    cartProducts.add(productDAO.getProduct(Integer.parseInt(productID)));
                    logger.info("Product (ID=" + productID + ") has been added to the cart");
                }

            } else {
                cartProducts = new ArrayList<>();
                cartProducts.add(productDAO.getProduct(Integer.parseInt(productID)));
                logger.info("Product (ID=" + productID + ") has been added to the cart");
                session.setAttribute("cartProducts", cartProducts);
            }
        }
    }

    private void getProductsOnPage() {
        productsAmount = products.size();

        products = productDAO.getProductsOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE, products);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / RECORDS_PER_PAGE);
    }

    private void getProductsFromPriceRange(HttpServletRequest request) {

        if (request.getParameter("minPrice") != null && request.getParameter("minPrice").length() > 0)
            minPrice = Integer.parseInt(request.getParameter("minPrice"));

        if (request.getParameter("maxPrice") != null && request.getParameter("maxPrice").length() > 0)
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));

        products = productDAO.getAllProducts(minPrice, maxPrice);
    }

    private void getProductsFromCategory() {

        if (selectedCategory != null && !products.isEmpty()) {

            if (!selectedCategory.equals("All"))
                products.removeIf(product -> !product.getCategory().equals(selectedCategory));
        }
    }

    private void sortProducts(String sortingOption) {

        if (sortingOption != null && !products.isEmpty()) {

            switch (sortingOption) {

                case "a-z":
                    Sorter.sortProductsAZ(products);
                    break;

                case "z-a":
                    Sorter.sortProductsZA(products);
                    break;

                case "cheap-expensive":
                    Sorter.sortProductsCheapExpensive(products);
                    break;

                case "expensive-cheap":
                    Sorter.sortProductsExpensiveCheap(products);
                    break;

                case "older-newer":
                    Sorter.sortProductsOlderNewer(products);
                    break;

                case "newer-older":
                    Sorter.sortProductsNewerOlder(products);
                    break;

                default:
                    break;
            }
        }
    }
}
