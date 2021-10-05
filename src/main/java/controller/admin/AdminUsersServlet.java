package controller.admin;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.dao.UserDAO;
import constant.Constants;

/**
 * Admin user controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminUsersServlet", urlPatterns = Constants.PATH_ADMIN_USERS)
public class AdminUsersServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUsersServlet.class);

    private final UserDAO userDAO;

    public AdminUsersServlet() {
        this.userDAO = new UserDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (setUserBlockStatus(request, response)) {
            return;
        }

        var pageNumber = 1;

        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        var users = userDAO.getUsersOnPage(
            (pageNumber - 1) * Constants.RECORDS_PER_PAGE,
            Constants.RECORDS_PER_PAGE
        );

        var pagesAmount = (int) Math.ceil(users.size() * 1.0 / Constants.RECORDS_PER_PAGE);

        request.setAttribute("users", users);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_ADMIN_USERS_JSP).forward(request, response);
    }

    /**
     * Sets user's block status.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether status is changed.
     *
     * @throws IOException If redirect failed.
     */
    private boolean setUserBlockStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var blockStatus = request.getParameter("blockStatus");
        var userID = request.getParameter("userID");

        if (blockStatus != null && userID != null) {
            userDAO.updateUserBlockStatus(blockStatus, Integer.parseInt(userID));
            LOGGER.info("Admin '{}' '{}' user with ID={}", request.getSession().getAttribute("userLogin"), blockStatus, userID);
            response.sendRedirect(Constants.PATH_ADMIN_USERS);
            return true;
        }

        return false;
    }
}