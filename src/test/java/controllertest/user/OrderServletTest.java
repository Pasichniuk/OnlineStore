package controllertest.user;

import controller.user.OrderServlet;

import org.junit.*;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class OrderServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PrintWriter printWriter;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        printWriter = mock(PrintWriter.class);
    }

    @Test
    public void testOrderServlet() throws IOException {
        final OrderServlet servlet = new OrderServlet();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userLogin")).thenReturn(null);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);
    }
}
