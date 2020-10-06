package controller;

import constant.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Logout servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "LogoutServlet", urlPatterns = Constants.PATH_LOGOUT)
public class LogoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(Constants.PATH_LOGIN);
    }
}
