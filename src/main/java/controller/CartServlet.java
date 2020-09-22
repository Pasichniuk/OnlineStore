package controller;

import dao.ProductDAO;
import entity.Cart;
import entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public CartServlet() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = Cart.getProducts();

        if (removeProductFromCart(request, response, products) || addProductToCart(request, response, products))
            return;

        request.setAttribute("totalPrice", Cart.getTotalPrice());
        request.setAttribute("products", products);

        request.getRequestDispatcher("view/cart-jsp.jsp").forward(request, response);
    }

    private boolean removeProductFromCart(HttpServletRequest request, HttpServletResponse response, List<Product> products) throws IOException {
        String productID = request.getParameter("ProductID");

        if (productID != null) {

            for (Product p : products) {

                if (p.getId() == Integer.parseInt(productID)) {
                    Cart.decreaseTotalPrice(p.getPrice());

                    products.remove(p);
                    response.sendRedirect("/cart");
                    return true;
                }
            }
        }

        return false;
    }

    private boolean addProductToCart(HttpServletRequest request, HttpServletResponse response, List<Product> products) throws IOException {
        Cookie[] cookies = request.getCookies();

        for (Cookie c : cookies) {

            if (c.getValue().length() < 10) {
                Product product = productDAO.getProduct(Integer.parseInt(c.getValue()));
                products.add(product);

                Cart.increaseTotalPrice(product.getPrice());

                Cookie cookie = new Cookie("ProductID", "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.sendRedirect("/catalog");
                return true;
            }
        }

        return false;
    }
}
