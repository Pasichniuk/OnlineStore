package controller;

import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    private static final String CHECK_INPUT_REGEX = "^[a-zA-Z0-9._-]{3,}$";

    private final UserDAO userDAO;

    public RegistrationServlet() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        if (userLogin.matches(CHECK_INPUT_REGEX) && userPassword.matches(CHECK_INPUT_REGEX) && confirmPassword.matches(CHECK_INPUT_REGEX)) {

            if (userPassword.equals(confirmPassword)) {

                if (userDAO.registerUser(userLogin, userPassword)) {
                    response.sendRedirect("/log-in");
                    return;
                }
            }
        }

        response.sendRedirect("/registration");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("view/registration-jsp.jsp").forward(request, response);
    }
}
