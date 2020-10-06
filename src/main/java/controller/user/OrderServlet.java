package controller.user;

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String userLogin = (String) session.getAttribute("userLogin");

        if (userLogin != null && session.getAttribute("cartProducts") != null) {
            orderDAO.insertOrder(userDAO.getUser(userLogin).getId(), (List<Product>) session.getAttribute("cartProducts"));
            session.setAttribute("cartProducts", null);

            logger.info("User '" + userLogin + "' created new order...");

            response.sendRedirect("/cart");

        } else
            response.getWriter().write(notifyUnauthorizedUser());
    }

    private String notifyUnauthorizedUser() {
        return "<script>" + "alert('Only authorized users can make orders. Cart cannot be empty.');" + "window.location = 'http://localhost:8080/cart';" + "</script>";
    }
}
