package controller.user;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.dao.ProductDAO;
import database.dao.CategoryDAO;
import util.Sorter;
import constant.Constants;
import entity.Product;

/**
 * Product controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "ProductServlet", urlPatterns = Constants.PATH_CATALOG)
@SuppressWarnings("unchecked")
public class ProductServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServlet.class);
    private static final int CART_LIMIT = 20;

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private String selectedCategory;
    private String sortingOption;

    public ProductServlet() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var session = request.getSession();
        var productID = request.getParameter("ProductID");
        var cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (productID != null) {

            var id = Integer.parseInt(productID);

            if (cartProducts != null) {

                if (getCartSize(cartProducts) < CART_LIMIT) {

                    if (!productIsAlreadyInCart(id, cartProducts)) {
                        cartProducts.add(productDAO.getProduct(id));
                    } else {
                        increaseProductCountInCart(id, cartProducts);
                    }

                    LOGGER.info("Product (ID={}) has been added to the cart", productID);
                    response.sendRedirect(Constants.PATH_CATALOG);

                } else {
                    response.getWriter().write(notifyCartIsFull());
                }

            } else {
                cartProducts = new ArrayList<>();
                cartProducts.add(productDAO.getProduct(Integer.parseInt(productID)));

                LOGGER.info("Product (ID={}) has been added to the cart", productID);

                session.setAttribute("cartProducts", cartProducts);
                response.sendRedirect(Constants.PATH_CATALOG);
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().setAttribute("categories", categoryDAO.getAllCategories());

        var minPrice = Constants.MIN_PRICE;
        var maxPrice = Constants.MAX_PRICE;

        if (request.getParameter("minPrice") != null && request.getParameter("minPrice").length() > 0) {
            minPrice = Integer.parseInt(request.getParameter("minPrice"));
        }

        if (request.getParameter("maxPrice") != null && request.getParameter("maxPrice").length() > 0) {
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
        }

        if (request.getParameter("Category") != null) {
            selectedCategory = request.getParameter("Category");
        }

        var pageNumber = 1;

        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        var products = productDAO.getProductsOnPage(
            (pageNumber - 1) * Constants.RECORDS_PER_PAGE,
            Constants.RECORDS_PER_PAGE,
            minPrice,
            maxPrice
        );

        if (selectedCategory != null && !products.isEmpty()) {

            if (!selectedCategory.equals("All")) {
                products.removeIf(product -> !selectedCategory.equals(product.getCategory()));
            }
        }

        Sorter.sortProductsAZ(products);

        if (request.getParameter("Sort") != null) {
            sortingOption = request.getParameter("Sort");
        }

        sortProducts(sortingOption, products);

        var pagesAmount = (int) Math.ceil(products.size() * 1.0 / Constants.RECORDS_PER_PAGE);

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_CATALOG_JSP).forward(request, response);
    }

    /**
     * Returns the amount of products in cart.
     *
     * @param cartProducts List of products in cart.
     *
     * @return Size of cart.
     */
    private int getCartSize(List<Product> cartProducts) {
        return cartProducts.stream()
            .mapToInt(Product::getCount)
            .sum();
    }

    /**
     * Checks whether same product is already in Cart.
     *
     * @param productID Product identifier.
     * @param cartProducts List of products in Cart.
     *
     * @return Whether product exists in Cart.
     */
    private boolean productIsAlreadyInCart(int productID, List<Product> cartProducts) {
        return cartProducts.stream()
            .anyMatch(product -> productID == product.getId());
    }

    /**
     * Increases the count of product.
     *
     * @param productID Product identifier.
     * @param products List of products.
     */
    private void increaseProductCountInCart(int productID, List<Product> products) {
        products.stream()
            .filter(product -> productID == product.getId())
            .forEach(product -> product.setCount(product.getCount() + 1));
    }

    private void sortProducts(String sortingOption, List<Product> products) {

        if (sortingOption == null || products.isEmpty()) {
            return;
        }

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

    private String notifyCartIsFull() {
        return "<script>alert('Cart is full!');window.location = 'http://localhost:8080/catalog';</script>";
    }
}