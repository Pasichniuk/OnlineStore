package controllertest.admin;

import controller.admin.AdminOrdersServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminOrdersServletTest {

    @Test
    public void testAdminOrdersServlet() throws ServletException, IOException {
        final AdminOrdersServlet servlet = new AdminOrdersServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("orderID")).thenReturn(String.valueOf(0));
        when(request.getParameter("status")).thenReturn(null);
        when(request.getParameter("page")).thenReturn(String.valueOf(1));
        when(request.getRequestDispatcher("view/admin/admin-orders-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);
    }
}
