package controllertest.admin;

import constant.Constants;
import controller.admin.AdminProductsServlet;

import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminProductsServletTest {

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
    public void testAdminProductsServlet() throws ServletException, IOException {
        final AdminProductsServlet servlet = new AdminProductsServlet();

        when(request.getParameter("productID")).thenReturn(String.valueOf(0));
        when(request.getParameter("productName")).thenReturn("test");
        when(request.getParameter("category")).thenReturn("test");
        when(request.getParameter("price")).thenReturn(String.valueOf(0));
        when(request.getParameter("action")).thenReturn("EDIT");
        when(request.getParameter("page")).thenReturn(String.valueOf(1));

        when(request.getSession()).thenReturn(session);

        when(request.getRequestDispatcher(Constants.PATH_ADMIN_CATALOG_JSP)).thenReturn(dispatcher);

        servlet.doPost(request, response);

        when(request.getParameter("action")).thenReturn("EDIT");
        servlet.doPost(request, response);

        servlet.doGet(request, response);
    }
}
