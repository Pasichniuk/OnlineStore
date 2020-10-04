package controllerTest.user;

import controller.user.OrderServlet;
import database.dao.*;
import entity.*;
import org.junit.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.mockito.Mockito.*;

public class OrderServletTest {

    @Test
    public void testOrderServlet() throws IOException {
        final OrderServlet servlet = new OrderServlet();

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final PrintWriter printWriter = mock(PrintWriter.class);
        final UserDAO userDAO = mock(UserDAO.class);
        final User user = mock(User.class);
        final Product product = mock(Product.class);
        final List<Product> products = new ArrayList<>();
        final String userLogin = "test";
        final Cart cart = mock(Cart.class);
        cart.getCartProducts().add(product);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userLogin")).thenReturn(userLogin);
        when(userDAO.getUser(userLogin)).thenReturn(user);
        when(user.getId()).thenReturn(0);
        when(cart.getCartProducts()).thenReturn(products);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);
    }
}
