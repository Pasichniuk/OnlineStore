package controllertest.user;

import controller.user.CartServlet;
import entity.Product;
import org.junit.*;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CartServletTest {

    @Mock
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private HttpSession session;
    private Product product;

    @Before
    public void beforeClass() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        product = mock(Product.class);
    }

    @Test
    public void testCartServlet() throws ServletException, IOException {
        final CartServlet servlet = new CartServlet();

        final List<Product> cartProducts = new ArrayList<>();
        cartProducts.add(product);

        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("cartProducts")).thenReturn(cartProducts);

        when(request.getParameter("ProductID")).thenReturn(String.valueOf(1));
        when(request.getParameter("page")).thenReturn(String.valueOf(1));

        when(request.getRequestDispatcher("view/user/cart-jsp.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        when(request.getParameter("ProductID")).thenReturn(null);

        servlet.doGet(request, response);

        servlet.doPost(request, response);

        when(session.getAttribute("cartProducts")).thenReturn(null);

        servlet.doGet(request, response);
    }
}
