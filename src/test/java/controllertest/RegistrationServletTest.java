package controllertest;

import controller.RegistrationServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class RegistrationServletTest {

    @Test
    public void testRegistrationServlet() throws IOException, ServletException {
        final RegistrationServlet servlet = new RegistrationServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final PrintWriter writer = mock(PrintWriter.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getParameter("login")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("confirm-password")).thenReturn("test");
        when(request.getParameter("userName")).thenReturn("Test Test");
        when(request.getParameter("userNameRU")).thenReturn("Тест Тест");
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
        when(request.getRequestDispatcher("view/registration-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);

        servlet.doPost(request, response);
    }
}
