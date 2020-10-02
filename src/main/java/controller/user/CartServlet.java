package controller.user;

import database.dao.ProductDAO;
import entity.Cart;
import entity.Product;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Cart servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CartServlet.class);

    private final ProductDAO productDAO;

    private List<Product> products;

    private static final int RECORDS_PER_PAGE = 5;
    private int pageNumber = 1;

    private int productsAmount;
    private int pagesAmount;

    public CartServlet() {
        productDAO = new ProductDAO();
        products = Cart.getCartProducts();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        products = Cart.getCartProducts();

        if (removeProductFromCart(request, response) || addProductToCart(request, response))
            return;

        productsAmount = products.size();

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsOnPage();

        request.setAttribute("totalPrice", Cart.getTotalPrice());
        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("view/user/cart-jsp.jsp").forward(request, response);
    }

    /**
     * Removes product from cart.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether product is removed.
     *
     * @throws IOException If redirect failed.
     */
    private boolean removeProductFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("ProductID");

        if (productID != null) {

            for (Product p : products) {

                if (p.getId() == Integer.parseInt(productID)) {
                    Cart.decreaseTotalPrice(p.getPrice());
                    products.remove(p);

                    logger.info("Product was removed from cart...");

                    response.sendRedirect("/cart");
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Adds products to cart.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether product is added.
     *
     * @throws IOException If redirect failed.
     */
    private boolean addProductToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();

        for (Cookie c : cookies) {

            if (c.getValue().length() < 10) {
                Product product = productDAO.getProduct(Integer.parseInt(c.getValue()));
                products.add(product);

                Cart.increaseTotalPrice(product.getPrice());

                Cookie cookie = new Cookie("ProductID", "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                logger.info("Product was added to cart...");

                response.sendRedirect("/catalog");
                return true;
            }
        }

        return false;
    }

    private void getProductsOnPage() {
        products = Cart.getCartProductsOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / RECORDS_PER_PAGE);
    }
}
