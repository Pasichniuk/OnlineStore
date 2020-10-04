package controllerTest.admin;

import controller.admin.AdminProductsServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminProductsServletTest {

    @Test
    public void testAdminProductsServlet() throws ServletException, IOException {
        final AdminProductsServlet servlet = new AdminProductsServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("productID")).thenReturn(String.valueOf(0));
        when(request.getParameter("productName")).thenReturn("test");
        when(request.getParameter("category")).thenReturn("test");
        when(request.getParameter("price")).thenReturn(String.valueOf(0));
        when(request.getParameter("action")).thenReturn("EDIT", "ADD");
        when(request.getParameter("page")).thenReturn(String.valueOf(1));
        when(request.getRequestDispatcher("view/admin/admin-catalog-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);
        servlet.doPost(request, response);
    }
}
