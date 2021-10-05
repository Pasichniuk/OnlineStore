package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import database.dao.UserDAO;
import constant.Constants;

/**
 * Registration servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "RegistrationServlet", urlPatterns = Constants.PATH_REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private static final String CHECK_INPUT_REGEX = "^[a-zA-Z0-9._-]{3,}$";
    private static final String CHECK_FULL_NAME_REGEX = "^[a-zA-Z]{2,}\\s[a-zA-Z]+";
    private static final String CHECK_FULL_NAME_RU_REGEX = "^[A-ЯЁ][а-яё]+\\s[A-ЯЁ][а-яё]+$";

    private final UserDAO userDAO;

    public RegistrationServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (validateUserInput(request, response)) {
            return;
        }

        response.getWriter().write(notifyIncorrectInput());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constants.PATH_REGISTRATION_JSP).forward(request, response);
    }

    /**
     * Validates the user input.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return If registration is successful
     *
     * @throws IOException If redirect failed.
     */
    private boolean validateUserInput(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var userLogin = request.getParameter("login");
        var userPassword = request.getParameter("password");
        var confirmPassword = request.getParameter("confirm-password");
        var userName = request.getParameter("userName");
        var userNameRU = new String(request.getParameter("userNameRU").getBytes(StandardCharsets.ISO_8859_1), "cp1251");

        if (userLogin.matches(CHECK_INPUT_REGEX) && userPassword.matches(CHECK_INPUT_REGEX) && confirmPassword.matches(CHECK_INPUT_REGEX) && userName.matches(CHECK_FULL_NAME_REGEX) && userNameRU.matches(CHECK_FULL_NAME_RU_REGEX)) {

            if (userPassword.equals(confirmPassword)) {

                if (userDAO.registerUser(userLogin, userPassword, userName, userNameRU)) {
                    response.sendRedirect(Constants.PATH_LOGIN);
                    return true;
                }
            }
        }

        return false;
    }

    private String notifyIncorrectInput() {
        return "<script>alert('Incorrect input! Please, try again.');" + "window.location = 'http://localhost:8080/registration';</script>";
    }
}