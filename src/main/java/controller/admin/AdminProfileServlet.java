package controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminProfileServlet", urlPatterns = "/admin-profile")
public class AdminProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String adminLogin = (String)session.getAttribute("adminLogin");

        request.setAttribute("adminLogin", adminLogin);

        request.getRequestDispatcher("view/admin/admin-profile-jsp.jsp").forward(request, response);
    }
}
