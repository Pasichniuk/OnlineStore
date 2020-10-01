package controller.user;

import database.dao.OrderDAO;
import database.dao.UserDAO;
import entity.Order;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProfileServlet.class);

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public ProfileServlet() {
        orderDAO = new OrderDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        setLanguage(request, session);

        String userLogin = (String)session.getAttribute("userLogin");

        List<Order> orders = orderDAO.getUserOrders(userDAO.getUser(userLogin).getId());

        request.setAttribute("userName", getUserFullName(session, userLogin));
        request.setAttribute("orders", orders);

        logger.info("User '" + userLogin + "' viewed his profile...");

        request.getRequestDispatcher("view/user/profile-jsp.jsp").forward(request, response);
    }

    private void setLanguage(HttpServletRequest request, HttpSession session) {
        String language = (String) session.getAttribute("lang");

        if (request.getParameter("lang") != null) {
            language = request.getParameter("lang");
            session.setAttribute("lang", language);
        }

        Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", language);
    }

    private String getUserFullName(HttpSession session, String userLogin) {

        if (session.getAttribute("lang") != null) {

            if (session.getAttribute("lang").equals("ru"))
                return userDAO.getUser(userLogin).getUserNameRU();
        }

        return userDAO.getUser(userLogin).getUserName();
    }
}
