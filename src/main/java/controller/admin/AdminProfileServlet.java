package controller.admin;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.dao.UserDAO;
import constant.Constants;

/**
 * Admin profile controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminProfileServlet", urlPatterns = Constants.PATH_ADMIN_PROFILE)
public class AdminProfileServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminProfileServlet.class);

    private final UserDAO userDAO;

    public AdminProfileServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var session = request.getSession();
        var adminLogin = (String) session.getAttribute("userLogin");

        setLanguage(request, session);

        request.setAttribute("adminName", getAdminFullName(session, adminLogin));

        LOGGER.info("Admin '{}' viewed his profile", adminLogin);

        request.getRequestDispatcher(Constants.PATH_ADMIN_PROFILE_JSP).forward(request, response);
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
     * Returns the full name of an admin.
     *
     * @param session Session.
     * @param adminLogin Login of Admin.
     *
     * @return Full name of Admin.
     */
    private String getAdminFullName(HttpSession session, String adminLogin) {

        if (session.getAttribute("lang") != null) {

            if ("en".equals(session.getAttribute("lang"))) {
                return userDAO.getUser(adminLogin).getUserName();
            }
        }

        return userDAO.getUser(adminLogin).getUserNameRU();
    }
}