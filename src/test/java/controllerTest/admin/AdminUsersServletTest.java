package controllerTest.admin;

import controller.admin.AdminUsersServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminUsersServletTest {

    @Test
    public void testAdminUsersServlet() throws ServletException, IOException {
        final AdminUsersServlet servlet = new AdminUsersServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("blockStatus")).thenReturn(null, "UNBLOCKED", "BLOCKED");
        when(request.getParameter("userID")).thenReturn(String.valueOf(0));
        when(request.getParameter("page")).thenReturn(String.valueOf(1));
        when(request.getRequestDispatcher("view/admin/admin-users-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);
    }
}
