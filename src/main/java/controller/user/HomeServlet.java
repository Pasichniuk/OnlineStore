package controller.user;

import constant.Constants;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Home servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "HomeServlet", urlPatterns = Constants.PATH_HOME)
public class HomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constants.PATH_HOME_JSP).forward(request, response);
    }
}
