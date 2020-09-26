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

    private String selectedCategory;

    private String sortingOption;

    private int minPrice = 0;
    private int maxPrice = 10_000;

    private static final int RECORDS_PER_PAGE = 5;
    private final int productsAmount;
    private int pageNumber = 1;
    private int pagesAmount;

    public ProductServlet() {
        productDAO = new ProductDAO();
        products = productDAO.getAllProducts(minPrice, maxPrice);
        productsAmount = products.size();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (sendProductToCart(request, response))
            return;

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getProductsFromPriceRange(request);

        if (request.getParameter("Category") != null)
            selectedCategory = request.getParameter("Category");

        getProductsFromCategory();

        if (request.getParameter("Sort") != null)
            sortingOption = request.getParameter("Sort");

        sortProducts(sortingOption);

        request.setAttribute("products", products);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

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

    private void getProductsOnPage() {
        products = productDAO.getProductsOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE, minPrice, maxPrice);

        pagesAmount = (int) Math.ceil(productsAmount * 1.0 / RECORDS_PER_PAGE);
    }

    private void getProductsFromPriceRange(HttpServletRequest request) {

        if (request.getParameter("minPrice") != null && request.getParameter("minPrice").length() > 0)
            minPrice = Integer.parseInt(request.getParameter("minPrice"));

        if (request.getParameter("maxPrice") != null && request.getParameter("maxPrice").length() > 0)
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));

        getProductsOnPage();
    }

    private void getProductsFromCategory() {

        if (selectedCategory != null && !products.isEmpty()) {

            if (selectedCategory.equals("All"))
                getProductsOnPage();
            else
                products.removeIf(product -> !product.getCategory().equals(selectedCategory));
        }
    }

    private void sortProducts(String sortingOption) {

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
