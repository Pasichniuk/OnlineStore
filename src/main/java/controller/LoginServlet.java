package controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.jsp.jstl.core.Config;

import database.dao.UserDAO;
import constant.Constants;

/**
 * Login controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "LoginServlet", urlPatterns = Constants.PATH_LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO;

    public LoginServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (authorizeUser(request, response)) {
            return;
        }

        response.getWriter().write(notifyIncorrectLoginInput());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        setLanguage(request);

        if (getProfile(request, response)) {
            return;
        }

        request.getRequestDispatcher(Constants.PATH_LOGIN_JSP).forward(request, response);
    }

    /**
     * Authorizes the user.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return If authorization is successful.
     *
     * @throws IOException If redirect failed.
     */
    private boolean authorizeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var userLogin = request.getParameter("login");
        var userPassword = request.getParameter("password");

        if (userDAO.authorizeUser(userLogin, userPassword)) {

            var session = request.getSession();
            var role = userDAO.getUser(userLogin).getRole();

            if (role.equals(Constants.ROLE_ADMIN)) {

                session.setAttribute("userLogin", userLogin);
                session.setAttribute("role", role);

                response.sendRedirect(Constants.PATH_ADMIN_PROFILE);

                return true;

            } else if (role.equals(Constants.ROLE_USER)) {

                session.setAttribute("userLogin", userLogin);
                session.setAttribute("role", role);

                response.sendRedirect(Constants.PATH_PROFILE);

                return true;
            }
        }

        return false;
    }

    /**
     * Provides the profile page.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether the profile was received.
     *
     * @throws IOException If redirect failed.
     */
    private boolean getProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var session = request.getSession();

        if (session.getAttribute("userLogin") != null && session.getAttribute("role") != null) {

            var role = (String) session.getAttribute("role");

            if (role.equals(Constants.ROLE_ADMIN)) {
                response.sendRedirect(Constants.PATH_ADMIN_PROFILE);
                return true;
            } else if (role.equals(Constants.ROLE_USER)) {
                response.sendRedirect(Constants.PATH_PROFILE);
                return true;
            }
        }

        return false;
    }

    /**
     * Sets application language.
     *
     * @param request Request.
     */
    private void setLanguage(HttpServletRequest request) {

        var language = (String) request.getSession().getAttribute("lang");

        if (request.getParameter("lang") != null) {
            language = request.getParameter("lang");
            request.getSession().setAttribute("lang", language);
        }

        Config.set(request.getSession(), "javax.servlet.jsp.jstl.fmt.locale", language);
    }

    private String notifyIncorrectLoginInput() {
        return "<script>alert('Incorrect login or password! Please, check your input.');window.location = 'http://localhost:8080/log-in';</script>";
    }
}