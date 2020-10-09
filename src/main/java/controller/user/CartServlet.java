package controller.user;

import constant.Constants;
import entity.Product;
import org.apache.log4j.Logger;

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

@WebServlet(name = "CartServlet", urlPatterns = Constants.PATH_CART)
public class CartServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CartServlet.class);

    private int pageNumber = 1;
    private int productsAmount;
    private int pagesAmount;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        removeProductFromCart(request);
        response.sendRedirect(Constants.PATH_CART);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<Product> cartProducts = (List<Product>) session.getAttribute("cartProducts");

        if (cartProducts == null) {
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPrice", 0);
            request.getRequestDispatcher(Constants.PATH_CART_JSP).forward(request, response);
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

        request.getRequestDispatcher(Constants.PATH_CART_JSP).forward(request, response);
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

            for (Product product : cartProducts) {

                if (product.getId() == Integer.parseInt(productID)) {
                    cartProducts.remove(product);
                    logger.info("Product (ID=" + productID + ") has been removed from the cart...");
                    return;
                }
            }
        }
    }

    /**
     * Gets total price of products.
     *
     * @param products List of products.
     *
     * @return Total price of products in list.
     */
    private String getTotalPrice(List<Product> products) {
        double totalPrice = 0;

        for (Product product : products) {
            totalPrice += product.getPrice() * product.getCount();
        }

        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(totalPrice);
    }

    private List<Product> getCartProductsOnPage(List<Product> cartProducts) {
        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / Constants.RECORDS_PER_PAGE);

        return getCartProductsOnPage((pageNumber-1) * Constants.RECORDS_PER_PAGE, cartProducts);
    }

    private List<Product> getCartProductsOnPage(int offset, List<Product> products) {
        List<Product> productsOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + Constants.RECORDS_PER_PAGE); i++) {

            if (i < products.size())
                productsOnPage.add(products.get(i));
        }

        return productsOnPage;
    }
}
