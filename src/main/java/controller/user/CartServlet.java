package controller.user;

import java.util.*;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constant.Constants;
import entity.Product;

/**
 * Cart controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "CartServlet", urlPatterns = Constants.PATH_CART)
@SuppressWarnings("unchecked")
public class CartServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        removeProductFromCart(request);
        response.sendRedirect(Constants.PATH_CART);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var session = request.getSession();
        var cartProducts = (List<Product>) session.getAttribute("cartProducts");
        var pageNumber = 1;

        if (cartProducts == null) {
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPrice", 0);
            request.getRequestDispatcher(Constants.PATH_CART_JSP).forward(request, response);
            return;
        }

        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        var pagesAmount = (int) Math.ceil(cartProducts.size() * 1.0 / Constants.RECORDS_PER_PAGE);
        var pageableProducts = getCartProductsOnPage((pageNumber - 1) * Constants.RECORDS_PER_PAGE, cartProducts);

        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPrice", getTotalPrice(cartProducts));
        request.setAttribute("products", pageableProducts);

        request.getRequestDispatcher(Constants.PATH_CART_JSP).forward(request, response);
    }

    /**
     * Removes the product from cart.
     *
     * @param request Request.
     *
     */
    private void removeProductFromCart(HttpServletRequest request) {

        var productID = request.getParameter("ProductID");
        var session = request.getSession();
        var cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (productID != null && cartProducts != null) {

            for (var product : cartProducts) {

                if (product.getId() == Integer.parseInt(productID)) {

                    if (product.getCount() == 1) {
                        cartProducts.remove(product);
                        LOGGER.info("Product (ID={}) has been removed from the cart", productID);
                    } else {
                        product.setCount(product.getCount() - 1);
                    }

                    return;
                }
            }
        }
    }

    /**
     * Gets the total price of cart products.
     *
     * @param products List of products.
     *
     * @return Total price of products in list.
     */
    private String getTotalPrice(List<Product> products) {
        return products.stream()
            .map(product -> product.getPrice() * product.getCount())
            .reduce(Double::sum)
            .map(price -> new DecimalFormat("#.##").format(price))
            .orElse("0");
    }

    private List<Product> getCartProductsOnPage(int offset, List<Product> products) {

        List<Product> productsOnPage = new ArrayList<>();

        for (int i = offset; i < offset + Constants.RECORDS_PER_PAGE; i++) {

            if (i < products.size()) {
                productsOnPage.add(products.get(i));
            }
        }

        return productsOnPage;
    }
}