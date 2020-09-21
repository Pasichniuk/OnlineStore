package controller;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/log-in")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO;

    public LoginServlet() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");

        if (userDAO.authorizeUser(userLogin, userPassword)) {
            HttpSession session = request.getSession();

            session.setAttribute("userLogin", userLogin);

            response.sendRedirect("/profile");

        } else response.sendRedirect("/log-in");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("userLogin") != null) {
            response.sendRedirect("/profile");
            return;
        }

        request.getRequestDispatcher("view/login-jsp.jsp").forward(request, response);
    }
}
