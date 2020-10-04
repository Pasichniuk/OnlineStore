package controllerTest.user;

import controller.user.ProductServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class ProductServletTest {

    @Test
    public void testProductServlet() throws ServletException, IOException {
        final ProductServlet servlet = new ProductServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("ProductID")).thenReturn(null, String.valueOf(0));
        when(request.getParameter("productName")).thenReturn("test");
        when(request.getParameter("Category")).thenReturn("test");
        when(request.getParameter("minPrice")).thenReturn(String.valueOf(100));
        when(request.getParameter("maxPrice")).thenReturn(String.valueOf(1200));
        when(request.getParameter("Sort")).thenReturn("a-z");
        when(request.getParameter("page")).thenReturn(String.valueOf(1));
        when(request.getRequestDispatcher("view/user/catalog-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);
    }
}
