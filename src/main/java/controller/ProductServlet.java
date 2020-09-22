package controller;

import dao.ProductDAO;
import entity.Product;
import util.Sorter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/catalog")
public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO;

    private String sortingOption;

    public ProductServlet() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productID = request.getParameter("ProductID");

        if (productID != null) {
            Cookie cookie = new Cookie("ProductID", productID);
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            response.sendRedirect("/cart");
            return;
        }

        List<Product> products = productDAO.getAllProducts();

        if (request.getParameter("Sort") != null)
            sortingOption = request.getParameter("Sort");

        sortProducts(sortingOption, products);

        request.setAttribute("products", products);

        RequestDispatcher dispatcher = request.getRequestDispatcher("view/catalog-jsp.jsp");
        dispatcher.forward(request, response);
    }

    private void sortProducts(String sortingOption, List<Product> products) {

        if (sortingOption != null) {

            switch (sortingOption) {
                case "a-z":
                    Sorter.sortProductsAZ(products);
                    break;

                case "z-a":
                    Sorter.sortProductsZA(products);
                    break;

                case "cheap-expensive":
                    Sorter.sortProductsCheapExpensive(products);
                    break;

                case "expensive-cheap":
                    Sorter.sortProductsExpensiveCheap(products);
                    break;

                case "older-newer":
                    Sorter.sortProductsOlderNewer(products);
                    break;

                case "newer-older":
                    Sorter.sortProductsNewerOlder(products);
                    break;

                default:
                    break;
            }
        }
    }
}
