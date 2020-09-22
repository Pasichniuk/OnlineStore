package controller;

import dao.ProductDAO;
import entity.Cart;
import entity.Product;

import javax.servlet.RequestDispatcher;
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

        String productID = request.getParameter("ProductID");

        if (productID != null) {

            for (Product p : products) {

                if (p.getId() == Integer.parseInt(productID)) {
                    Cart.decreaseTotalPrice(p.getPrice());

                    products.remove(p);
                    response.sendRedirect("/cart");
                    return;
                }
            }
        }

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
                return;
            }

        }

        request.setAttribute("totalPrice", Cart.getTotalPrice());

        request.setAttribute("products", products);

        RequestDispatcher dispatcher = request.getRequestDispatcher("view/cart-jsp.jsp");
        dispatcher.forward(request, response);
    }
}
