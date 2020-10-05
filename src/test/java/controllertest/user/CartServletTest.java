package controllertest.user;

import controller.user.CartServlet;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CartServletTest {

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
    public void testCartServlet() throws ServletException, IOException {
        final CartServlet servlet = new CartServlet();

        final Cookie[] cookie = { mock(Cookie.class) };

        when(request.getSession()).thenReturn(session);

        when(request.getCookies()).thenReturn(cookie);
        when(cookie[0].getValue()).thenReturn(String.valueOf(0));

        when(request.getParameter("ProductID")).thenReturn(null);
        when(request.getParameter("page")).thenReturn(String.valueOf(1));

        when(request.getRequestDispatcher("view/user/cart-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        when(request.getParameter("ProductID")).thenReturn(null);
        when(cookie[0].getValue()).thenReturn("JSESSIONID");

        servlet.doGet(request, response);
    }
}
