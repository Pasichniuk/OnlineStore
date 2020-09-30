package controller.admin;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

@WebServlet(name = "AdminProfileServlet", urlPatterns = "/admin-profile")
public class AdminProfileServlet extends HttpServlet {

    private final UserDAO userDAO;

    public AdminProfileServlet() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        setLanguage(request, session);

        String adminLogin = (String)session.getAttribute("userLogin");

        request.setAttribute("adminName", getAdminFullName(session, adminLogin));

        request.getRequestDispatcher("view/admin/admin-profile-jsp.jsp").forward(request, response);
    }

    private void setLanguage(HttpServletRequest request, HttpSession session) {
        String language = (String) session.getAttribute("lang");

        if (request.getParameter("lang") != null) {
            language = request.getParameter("lang");
            session.setAttribute("lang", language);
        }

        Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", language);
    }

    private String getAdminFullName(HttpSession session, String adminLogin) {

        if (session.getAttribute("lang") != null) {

            if (session.getAttribute("lang").equals("ru"))
                return userDAO.getUser(adminLogin).getUserNameRU();
        }

        return userDAO.getUser(adminLogin).getUserName();
    }
}
