package controller;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/log-in")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO;

    public LoginServlet() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (authorizeUser(request, response))
            return;

        response.getWriter().write(notifyIncorrectLoginInput());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setLanguage(request);

        if (getProfile(request, response))
            return;

        request.getRequestDispatcher("view/login-jsp.jsp").forward(request, response);
    }

    private boolean authorizeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");

        if (userDAO.authorizeUser(userLogin, userPassword)) {
            HttpSession session = request.getSession();

            String role = userDAO.getUser(userLogin).getRole();

            if (role.equals("ROLE_ADMIN")) {
                session.setAttribute("userLogin", userLogin);
                session.setAttribute("role", role);

                response.sendRedirect("/admin-profile");
                return true;

            } else if (role.equals("ROLE_USER")) {
                session.setAttribute("userLogin", userLogin);
                session.setAttribute("role", role);

                response.sendRedirect("/profile");
                return true;
            }
        }

        return false;
    }

    private boolean getProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("userLogin") != null && session.getAttribute("role") != null) {

            String role = (String) session.getAttribute("role");

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin-profile");
                return true;
            } else if (role.equals("ROLE_USER")) {
                response.sendRedirect("/profile");
                return true;
            }
        }

        return false;
    }

    private void setLanguage(HttpServletRequest request) {
        String language = (String) request.getSession().getAttribute("lang");

        if (request.getParameter("lang") != null) {
            language = request.getParameter("lang");
            request.getSession().setAttribute("lang", language);
        }

        Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", language);
    }

    private String notifyIncorrectLoginInput() {
        return "<script>" + "alert('Incorrect login or password! Please, check your input.');" + "window.location = 'http://localhost:8080/log-in';" + "</script>";
    }
}
