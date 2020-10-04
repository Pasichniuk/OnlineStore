package controllerTest.user;

import controller.user.HomeServlet;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class HomeServletTest {

    @Test
    public void testHomeServlet() throws ServletException, IOException {
        final HomeServlet homeServlet = new HomeServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("view/user/home-jsp.jsp")).thenReturn(dispatcher);

        homeServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
