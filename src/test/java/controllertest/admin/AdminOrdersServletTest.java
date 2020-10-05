package controllertest.admin;

import controller.admin.AdminOrdersServlet;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminOrdersServletTest {

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
    public void testAdminOrdersServlet() throws ServletException, IOException {
        final AdminOrdersServlet servlet = new AdminOrdersServlet();

        when(request.getParameter("orderID")).thenReturn(String.valueOf(0));
        when(request.getParameter("status")).thenReturn(null);
        when(request.getParameter("page")).thenReturn(String.valueOf(1));

        when(request.getSession()).thenReturn(session);

        when(request.getRequestDispatcher("view/admin/admin-orders-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);
    }
}
