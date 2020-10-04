package controllertest.admin;

import controller.admin.AdminProfileServlet;
import database.dao.UserDAO;
import entity.User;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminProfileServletTest {

    @Test
    public void testAdminProfileServlet() throws ServletException, IOException {
        final AdminProfileServlet servlet = new AdminProfileServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);
        final UserDAO userDAO = mock(UserDAO.class);
        final User user = mock(User.class);
        final String adminLogin = "test";

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("lang")).thenReturn("ru","en");
        when(session.getAttribute("lang")).thenReturn("ru", "en");
        when(session.getAttribute("userLogin")).thenReturn(adminLogin);
        when(userDAO.getUser(adminLogin)).thenReturn(user);
        when(user.getUserName()).thenReturn("Test Test");
        when(user.getUserNameRU()).thenReturn("Тест Тест");
        when(request.getRequestDispatcher("view/admin/admin-profile-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
