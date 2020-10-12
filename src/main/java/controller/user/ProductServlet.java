package controller.user;

import constant.Constants;
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

@WebServlet(name = "ProductServlet", urlPatterns = Constants.PATH_CATALOG)
public class ProductServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProductServlet.class);

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private List<Product> products;

    private String selectedCategory;
    private String sortingOption;

    private int minPrice = 0;
    private int maxPrice = 10_000;

    private int pageNumber = 1;

    private int productsAmount;
    private int pagesAmount;

    public ProductServlet() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        products = productDAO.getAllProducts(minPrice, maxPrice);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        addProductToCart(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("categories", categoryDAO.getAllCategories());

        productsAmount = products.size();

        getProductsFromPriceRange(request);

        if (request.getParameter("Category") != null)
            selectedCategory = request.getParameter("Category");

        getProductsFromCategory();

        Sorter.sortProductsAZ(products);

        if (request.getParameter("Sort") != null)
            sortingOption = request.getParameter("Sort");

        sortProducts(sortingOption);

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsOnPage();

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_CATALOG_JSP).forward(request, response);
    }

    /**
     * Adds product to cart.
     *
     * @param request Request.
     * @param response Response.
     *
     * @throws IOException If getWriter() or redirect failed.
     */
    private void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("ProductID");

        HttpSession session = request.getSession();

        List<Product> cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (productID != null) {

            int id = Integer.parseInt(productID);

            if (cartProducts != null) {

                if (getCartSize(cartProducts) < Constants.CART_LIMIT) {

                    if (!productIsAlreadyInCart(id, cartProducts))
                        cartProducts.add(productDAO.getProduct(id));
                    else
                        increaseProductCountInCart(id, cartProducts);

                    logger.info("Product (ID=" + productID + ") has been added to the cart");
                    response.sendRedirect(Constants.PATH_CATALOG);
                } else
                    response.getWriter().write(notifyCartIsFull());

            } else {
                cartProducts = new ArrayList<>();
                cartProducts.add(productDAO.getProduct(Integer.parseInt(productID)));

                logger.info("Product (ID=" + productID + ") has been added to the cart");
                session.setAttribute("cartProducts", cartProducts);
                response.sendRedirect(Constants.PATH_CATALOG);
            }
        }
    }

    /**
     * Returns amount of products in Cart.
     *
     * @param cartProducts List of products in Cart.
     *
     * @return Size of cart.
     */
    private int getCartSize(List<Product> cartProducts) {
        int size = 0;

        for (Product product : cartProducts)
            size += product.getCount();

        return size;
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

        for (Product product : cartProducts) {

            if (product.getId() == productID)
                return true;
        }

        return false;
    }

    /**
     * Increases count of product.
     *
     * @param productID Product identifier.
     * @param products List of products.
     */
    private void increaseProductCountInCart(int productID, List<Product> products) {

        for (Product product : products) {

            if (product.getId() == productID)
                product.setCount(product.getCount() + 1);
        }
    }

    private void getProductsOnPage() {
        productsAmount = products.size();

        products = productDAO.getProductsOnPage((pageNumber-1) * Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE, products);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / Constants.RECORDS_PER_PAGE);
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

    private String notifyCartIsFull() {
        return "<script>" + "alert('Cart is full!');" + "window.location = 'http://localhost:8080/catalog';" + "</script>";
    }
}