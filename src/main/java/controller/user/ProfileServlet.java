package controller.user;

import dao.OrderDAO;
import dao.UserDAO;
import entity.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public ProfileServlet() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String userLogin = (String)session.getAttribute("userLogin");

        List<Order> orders = orderDAO.getUserOrders(userDAO.getUser(userLogin).getId());

        request.setAttribute("userLogin", userLogin);
        request.setAttribute("orders", orders);

        request.getRequestDispatcher("view/user/profile-jsp.jsp").forward(request, response);
    }
}
