package controller.admin;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        setLanguage(request, session);

        String adminLogin = (String)session.getAttribute("userLogin");

        request.setAttribute("adminLogin", adminLogin);

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
}
