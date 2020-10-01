package controller.admin;

import database.dao.UserDAO;
import entity.User;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminUsersServlet", urlPatterns = "/admin-users")
public class AdminUsersServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminUsersServlet.class);

    private final UserDAO userDAO;

    private List<User> users;

    private static final int RECORDS_PER_PAGE = 5;
    private final int usersAmount;
    private int pageNumber = 1;
    private int pagesAmount;

    public AdminUsersServlet() {
        userDAO = new UserDAO();
        users = userDAO.getAllUsers();
        usersAmount = users.size();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (setUserBlockStatus(request, response))
            return;

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getUsersOnPage();

        request.setAttribute("users", users);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("view/admin/admin-users-jsp.jsp").forward(request, response);
    }

    private boolean setUserBlockStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String blockStatus = request.getParameter("blockStatus");
        String userID = request.getParameter("userID");

        if (blockStatus != null && userID != null) {
            userDAO.updateUserBlockStatus(blockStatus, Integer.parseInt(userID));
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' " + blockStatus + " user with ID=" + userID);
            response.sendRedirect("/admin-users");
            return true;
        }

        return false;
    }

    private void getUsersOnPage() {
        users = userDAO.getUsersOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        pagesAmount = (int) Math.ceil(usersAmount * 1.0 / RECORDS_PER_PAGE);
    }
}
