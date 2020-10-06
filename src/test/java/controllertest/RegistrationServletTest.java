package controllertest;

import constant.Constants;
import controller.RegistrationServlet;

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

public class RegistrationServletTest {

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
    public void testRegistrationServlet() throws IOException, ServletException {
        final RegistrationServlet servlet = new RegistrationServlet();

        when(request.getParameter("login")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("confirm-password")).thenReturn("test");
        when(request.getParameter("userName")).thenReturn("Test Test");
        when(request.getParameter("userNameRU")).thenReturn("Тест Тест");

        when(request.getSession()).thenReturn(session);

        when(response.getWriter()).thenReturn(writer);

        when(request.getRequestDispatcher(Constants.PATH_REGISTRATION_JSP)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);

        servlet.doPost(request, response);
    }
}
