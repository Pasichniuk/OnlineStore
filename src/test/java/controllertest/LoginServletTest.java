package controllertest;

import controller.LoginServlet;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private PrintWriter writer;
    private HttpSession session;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        writer = mock(PrintWriter.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void testLoginServlet() throws IOException, ServletException {
        final LoginServlet servlet = new LoginServlet();

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
