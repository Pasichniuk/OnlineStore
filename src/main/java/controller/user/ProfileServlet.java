package controller.user;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.dao.OrderDAO;
import database.dao.UserDAO;
import constant.Constants;

/**
 * User profile controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "ProfileServlet", urlPatterns = Constants.PATH_PROFILE)
public class ProfileServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServlet.class);

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public ProfileServlet() {
        this.orderDAO = new OrderDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var session = request.getSession();
        var userLogin = (String)session.getAttribute("userLogin");

        setLanguage(request, session);

        var orders = orderDAO.getUserOrders(userDAO.getUser(userLogin).getId());

        request.setAttribute("userName", getUserFullName(session, userLogin));
        request.setAttribute("orders", orders);

        LOGGER.info("User '{}' viewed his profile", userLogin);

        request.getRequestDispatcher(Constants.PATH_PROFILE_JSP).forward(request, response);
    }

    /**
     * Sets the application language.
     *
     * @param request Request.
     * @param session Session.
     */
    private void setLanguage(HttpServletRequest request, HttpSession session) {

        var language = (String) session.getAttribute("lang");

        if (request.getParameter("lang") != null) {
            language = request.getParameter("lang");
            session.setAttribute("lang", language);
        }

        Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", language);
    }

    /**
     * Returns the full name of user.
     *
     * @param session Session.
     * @param userLogin Login of User.
     *
     * @return User's full name.
     */
    private String getUserFullName(HttpSession session, String userLogin) {

        if (session.getAttribute("lang") != null) {

            if ("en".equals(session.getAttribute("lang"))) {
                return userDAO.getUser(userLogin).getUserName();
            }
        }

        return userDAO.getUser(userLogin).getUserNameRU();
    }
}