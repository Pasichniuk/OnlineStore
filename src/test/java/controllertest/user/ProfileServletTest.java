package controllertest.user;

import constant.Constants;
import controller.user.ProfileServlet;
import database.dao.OrderDAO;
import database.dao.UserDAO;
import entity.Order;
import entity.User;

import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;

public class ProfileServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private User user;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        orderDAO = mock(OrderDAO.class);
        userDAO = mock(UserDAO.class);
        user = mock(User.class);
    }

    @Test
    public void testProfileServlet() throws ServletException, IOException {
        final ProfileServlet profileServlet = new ProfileServlet();

        final List<Order> orders = new ArrayList<>();
        final String userLogin = "test";

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("userLogin")).thenReturn("test");

        when(request.getParameter("lang")).thenReturn("en");

        when(userDAO.getUser(userLogin)).thenReturn(user);
        when(user.getId()).thenReturn(0);

        when(orderDAO.getUserOrders(user.getId())).thenReturn(orders);

        when(request.getRequestDispatcher(Constants.PATH_PROFILE_JSP)).thenReturn(dispatcher);

        profileServlet.doGet(request, response);

        verify(request).getSession();
        verify(dispatcher).forward(request, response);
    }
}
