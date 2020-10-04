package controllertest;

import controller.LogoutServlet;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {

    @Test
    public void testLogoutServlet() throws IOException {
        final LogoutServlet servlet = new LogoutServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);

        servlet.doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/log-in");
    }
}
