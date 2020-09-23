package controller.admin;

import dao.UserDAO;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminUsersServlet", urlPatterns = "/admin-users")
public class AdminUsersServlet extends HttpServlet {

    private final UserDAO userDAO;

    public AdminUsersServlet() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDAO.getAllUsers();

        request.setAttribute("users", users);

        request.getRequestDispatcher("view/admin/admin-users-jsp.jsp").forward(request, response);
    }
}
