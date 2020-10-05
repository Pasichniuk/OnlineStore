package controllertest.user;

import controller.user.HomeServlet;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class HomeServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void testHomeServlet() throws ServletException, IOException {
        final HomeServlet homeServlet = new HomeServlet();

        when(request.getRequestDispatcher("view/user/home-jsp.jsp")).thenReturn(dispatcher);

        homeServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
