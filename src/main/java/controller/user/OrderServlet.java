package controller.user;

import java.util.List;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.dao.*;
import entity.Product;
import constant.Constants;

/**
 * Order controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "OrderServlet", urlPatterns = Constants.PATH_ORDERS)
@SuppressWarnings("unchecked")
public class OrderServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public OrderServlet() {
        this.productDAO = new ProductDAO();
        this.orderDAO = new OrderDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var session = request.getSession();

        var userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.getWriter().write(notifyUnauthorizedUser());
            return;
        }

        createOrder(session, response, userLogin);
    }

    /**
     * Creates a new order.
     *
     * @param session Session.
     * @param response Response.
     * @param userLogin Login of User.
     *
     * @throws IOException If redirect failed.
     */
    private void createOrder(HttpSession session, HttpServletResponse response, String userLogin) throws IOException {

        var cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (cartProducts != null && !cartProducts.isEmpty()) {

            if (productsIsEnough(cartProducts)) {
                reserveProducts(cartProducts);
                orderDAO.insertOrder(userDAO.getUser(userLogin).getId(), cartProducts);
                session.setAttribute("cartProducts", null);
                LOGGER.info("User '{}' created a new order", userLogin);
                response.sendRedirect(Constants.PATH_CART);
            } else {
                response.getWriter().write(notifyWeDontHaveAsMuchProducts());
            }

        } else {
            response.getWriter().write(notifyCartIsEmpty());
        }
    }

    /**
     * Checks whether there is enough products in store.
     *
     * @param cartProducts List of products in Cart.
     *
     * @return Whether there is enough products in store.
     */
    private boolean productsIsEnough(List<Product> cartProducts) {

        var products = productDAO.getAllProducts(Constants.MIN_PRICE, Constants.MAX_PRICE);

        for (var p : cartProducts) {

            var product = products.get(p.getId() - 1);

            if (product.getCount() - product.getReserve() < p.getCount()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Reserves products in store.
     *
     * @param cartProducts List of products in Cart.
     */
    private void reserveProducts(List<Product> cartProducts) {

        var products = productDAO.getAllProducts(Constants.MIN_PRICE, Constants.MAX_PRICE);

        for (var p : cartProducts) {
            productDAO.setProductReserve(p.getId(), products.get(p.getId()).getReserve() + p.getCount());
        }
    }

    private String notifyUnauthorizedUser() {
        return "<script>alert('Only authorized users can make orders!');window.location = 'http://localhost:8080/cart';</script>";
    }

    private String notifyCartIsEmpty() {
        return "<script>alert('Cart is empty!');window.location = 'http://localhost:8080/cart';</script>";
    }

    private String notifyWeDontHaveAsMuchProducts() {
        return "<script>alert('We dont have as much products as you want!');window.location = 'http://localhost:8080/cart';</script>";
    }
}