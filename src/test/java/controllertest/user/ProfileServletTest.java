package controllertest.user;

import controller.user.ProfileServlet;
import database.dao.OrderDAO;
import database.dao.UserDAO;
import entity.Order;
import entity.User;
import org.junit.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;

public class ProfileServletTest {

    @Test
    public void testProfileServlet() throws ServletException, IOException {
        final ProfileServlet profileServlet = new ProfileServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession session = mock(HttpSession.class);
        final OrderDAO orderDAO = mock(OrderDAO.class);
        final UserDAO userDAO = mock(UserDAO.class);
        final User user = mock(User.class);
        final List<Order> orders = new ArrayList<>();
        final String userLogin = "test";

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("lang")).thenReturn("en");
        when(session.getAttribute("userLogin")).thenReturn("test");
        when(userDAO.getUser(userLogin)).thenReturn(user);
        when(user.getId()).thenReturn(0);
        when(orderDAO.getUserOrders(userDAO.getUser(userLogin).getId())).thenReturn(orders);
        when(request.getRequestDispatcher("view/user/profile-jsp.jsp")).thenReturn(dispatcher);

        profileServlet.doGet(request, response);

        verify(request).getSession();
        verify(dispatcher).forward(request, response);
    }
}
