package controllertest.admin;

import controller.admin.AdminProfileServlet;
import database.dao.UserDAO;
import entity.User;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminProfileServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;
    private UserDAO userDAO;
    private User user;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAO.class);
        user = mock(User.class);
    }

    @Test
    public void testAdminProfileServlet() throws ServletException, IOException {
        final AdminProfileServlet servlet = new AdminProfileServlet();

        final String adminLogin = "test";

        when(request.getParameter("lang")).thenReturn("ru");

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("lang")).thenReturn("ru");
        when(session.getAttribute("userLogin")).thenReturn(adminLogin);

        when(userDAO.getUser(adminLogin)).thenReturn(user);
        when(user.getUserName()).thenReturn("Test Test");
        when(user.getUserNameRU()).thenReturn("Test Test");

        when(request.getRequestDispatcher("view/admin/admin-profile-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
