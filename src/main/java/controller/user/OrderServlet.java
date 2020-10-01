package controller.user;

import database.dao.OrderDAO;
import database.dao.UserDAO;
import entity.Cart;

import org.apache.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(OrderServlet.class);

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public OrderServlet() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = (String) request.getSession().getAttribute("userLogin");

        if (userLogin != null && Cart.getCartProducts().size() > 0) {
            orderDAO.insertOrder(userDAO.getUser(userLogin).getId(), Cart.getCartProducts());
            Cart.clearCart();

            logger.info("User '" + userLogin + "' created new order...");

            response.sendRedirect("/cart");

        } else
            response.getWriter().write(notifyUnauthorizedUser());
    }

    private String notifyUnauthorizedUser() {
        return "<script>" + "alert('Only authorized users can make orders. Cart cannot be empty.');" + "window.location = 'http://localhost:8080/cart';" + "</script>";
    }
}
