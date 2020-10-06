package controller.admin;

import database.dao.UserDAO;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Admin Profile servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminProfileServlet", urlPatterns = "/admin-profile")
public class AdminProfileServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminProfileServlet.class);

    private final UserDAO userDAO;

    public AdminProfileServlet() {
        userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        setLanguage(request, session);

        String adminLogin = (String)session.getAttribute("userLogin");

        request.setAttribute("adminName", getAdminFullName(session, adminLogin));

        logger.info("Admin '" + adminLogin + "' viewed his profile...");

        request.getRequestDispatcher("view/admin/admin-profile-jsp.jsp").forward(request, response);
    }

    /**
     * Sets application language.
     *
     * @param request Request.
     * @param session Session.
     */
    private void setLanguage(HttpServletRequest request, HttpSession session) {
        String language = (String) session.getAttribute("lang");

        if (request.getParameter("lang") != null) {
            language = request.getParameter("lang");
            session.setAttribute("lang", language);
        }

        Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", language);
    }

    /**
     * Returns full name of Admin.
     *
     * @param session Session.
     * @param adminLogin Login of Admin.
     *
     * @return Full name of Admin.
     */
    private String getAdminFullName(HttpSession session, String adminLogin) {

        if (session.getAttribute("lang") != null) {

            if (session.getAttribute("lang").equals("en"))
                return userDAO.getUser(adminLogin).getUserName();
        }

        return userDAO.getUser(adminLogin).getUserNameRU();
    }
}
