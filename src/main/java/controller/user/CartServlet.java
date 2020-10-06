package controller.user;

import entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Cart servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 5;
    private int pageNumber = 1;

    private int productsAmount;
    private int pagesAmount;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        removeProductFromCart(request);

        response.sendRedirect("/cart");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<Product> cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (cartProducts == null) {
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPrice", 0);
            request.getRequestDispatcher("view/user/cart-jsp.jsp").forward(request, response);
            return;
        }

        productsAmount = cartProducts.size();

        if (request.getParameter("page") != null)
           pageNumber = Integer.parseInt(request.getParameter("page"));

        List<Product> cartProductsOnPage = getCartProductsOnPage(cartProducts);

        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPrice", getTotalPrice(cartProducts));
        request.setAttribute("products", cartProductsOnPage);

        request.getRequestDispatcher("view/user/cart-jsp.jsp").forward(request, response);
    }

    /**
     * Removes product from cart.
     *
     * @param request Request.
     *
     */
    private void removeProductFromCart(HttpServletRequest request) {
        String productID = request.getParameter("ProductID");

        HttpSession session = request.getSession();

        List<Product> cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (productID != null && cartProducts != null) {
            cartProducts.removeIf(p -> p.getId() == Integer.parseInt(productID));
        }
    }

    private String getTotalPrice(List<Product> products) {
        double totalPrice = 0;

        for (Product product : products)
            totalPrice += product.getPrice();

        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(totalPrice);
    }

    private List<Product> getCartProductsOnPage(List<Product> cartProducts) {
        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / RECORDS_PER_PAGE);

        return getCartProductsOnPage((pageNumber-1)*RECORDS_PER_PAGE, cartProducts);
    }

    private List<Product> getCartProductsOnPage(int offset, List<Product> products) {
        List<Product> productsOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + RECORDS_PER_PAGE); i++) {

            if (i < products.size())
                productsOnPage.add(products.get(i));
        }

        return productsOnPage;
    }
}
