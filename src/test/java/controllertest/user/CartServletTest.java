package controllertest.user;

import controller.user.CartServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CartServletTest {

    @Test
    public void testCartServlet() throws ServletException, IOException {
        final CartServlet servlet = new CartServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);
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
