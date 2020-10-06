package controllertest.user;

import controller.user.ProductServlet;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class ProductServletTest {

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
    public void testProductServlet() throws ServletException, IOException {
        final ProductServlet servlet = new ProductServlet();

        when(request.getParameter("ProductID")).thenReturn(String.valueOf(0));
        when(request.getParameter("productName")).thenReturn("test");
        when(request.getParameter("Category")).thenReturn("test");

        when(request.getParameter("minPrice")).thenReturn(String.valueOf(100));
        when(request.getParameter("maxPrice")).thenReturn(String.valueOf(1200));

        when(request.getParameter("Sort")).thenReturn("a-z");

        when(request.getParameter("page")).thenReturn(String.valueOf(1));

        when(request.getSession()).thenReturn(session);

        when(request.getRequestDispatcher("view/user/catalog-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        servlet.doPost(request, response);

        when(request.getParameter("ProductID")).thenReturn(String.valueOf(0));

        servlet.doPost(request, response);
    }
}
