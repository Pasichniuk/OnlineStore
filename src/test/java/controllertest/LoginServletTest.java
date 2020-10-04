package controllertest;

import controller.LoginServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @Test
    public void testLoginServlet() throws IOException, ServletException {
        final LoginServlet servlet = new LoginServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final PrintWriter writer = mock(PrintWriter.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getParameter("lang")).thenReturn("en");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("lang")).thenReturn("en");
        when(session.getAttribute("userLogin")).thenReturn("test");
        when(session.getAttribute("role")).thenReturn("ROLE_USER");
        when(response.getWriter()).thenReturn(writer);
        when(request.getRequestDispatcher("view/login-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        when(session.getAttribute("role")).thenReturn("ROLE_ADMIN");
        servlet.doGet(request, response);

        when(session.getAttribute("role")).thenReturn(null);
        servlet.doGet(request, response);

        servlet.doPost(request, response);
    }

}
