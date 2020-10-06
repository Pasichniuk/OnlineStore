package controller.user;

import constant.Constants;
import database.dao.OrderDAO;
import database.dao.UserDAO;

import entity.Product;
import org.apache.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Order servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "OrderServlet", urlPatterns = Constants.PATH_ORDERS)
public class OrderServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(OrderServlet.class);

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public OrderServlet() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin == null) {
            response.getWriter().write(notifyUnauthorizedUser());
            return;
        }

        List<Product> cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (cartProducts != null && !cartProducts.isEmpty()) {
            orderDAO.insertOrder(userDAO.getUser(userLogin).getId(), cartProducts);
            session.setAttribute("cartProducts", null);

            logger.info("User '" + userLogin + "' created new order...");

            response.sendRedirect(Constants.PATH_CART);

        } else
            response.getWriter().write(notifyCartIsEmpty());
    }

    private String notifyUnauthorizedUser() {
        return "<script>" + "alert('Only authorized users can make orders!');" + "window.location = 'http://localhost:8080/cart';" + "</script>";
    }

    private String notifyCartIsEmpty() {
        return "<script>" + "alert('Cart is empty!');" + "window.location = 'http://localhost:8080/cart';" + "</script>";
    }
}
