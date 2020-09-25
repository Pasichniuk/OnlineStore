package controller.user;

import dao.ProductDAO;
import entity.Product;
import util.Sorter;

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

    private List<Product> products;

    private String sortingOption;

    public ProductServlet() {
        productDAO = new ProductDAO();
        products = productDAO.getAllProducts(0, 10_000);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        products = getProductsFromPriceRange(request);

        response.sendRedirect("/catalog");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (sendProductToCart(request, response))
            return;

        if (request.getParameter("Sort") != null)
            sortingOption = request.getParameter("Sort");

        sortProducts(sortingOption, products);

        request.setAttribute("products", products);

        request.getRequestDispatcher("view/user/catalog-jsp.jsp").forward(request, response);
    }

    private boolean sendProductToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("ProductID");

        if (productID != null) {
            Cookie cookie = new Cookie("ProductID", productID);
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
            response.sendRedirect("/cart");
            return true;
        }

        return false;
    }

    private List<Product> getProductsFromPriceRange(HttpServletRequest request) {
        int minPrice = 0;
        int maxPrice = 10_000;

        if (request.getParameter("minPrice") != null && request.getParameter("minPrice").length() > 0)
            minPrice = Integer.parseInt(request.getParameter("minPrice"));

        if (request.getParameter("maxPrice") != null && request.getParameter("maxPrice").length() > 0)
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));

        return productDAO.getAllProducts(minPrice, maxPrice);
    }

    private void sortProducts(String sortingOption, List<Product> products) {

        if (sortingOption != null && !products.isEmpty()) {

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
