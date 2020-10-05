package controllertest.admin;

import controller.admin.AdminUsersServlet;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminUsersServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void testAdminUsersServlet() throws ServletException, IOException {
        final AdminUsersServlet servlet = new AdminUsersServlet();

        when(request.getParameter("blockStatus")).thenReturn(null, "UNBLOCKED", "BLOCKED");
        when(request.getParameter("userID")).thenReturn(String.valueOf(0));
        when(request.getParameter("page")).thenReturn(String.valueOf(1));

        when(request.getSession()).thenReturn(session);

        when(request.getRequestDispatcher("view/admin/admin-users-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);
    }
}
