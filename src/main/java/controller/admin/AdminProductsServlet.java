package controller.admin;

import dao.ProductDAO;
import entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminProductsServlet", urlPatterns = "/admin-catalog")
public class AdminProductsServlet extends HttpServlet {

    private final ProductDAO productDAO;

    public AdminProductsServlet() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (performAction(request, response))
            return;

        List<Product> products = productDAO.getAllProducts();

        request.setAttribute("products", products);

        request.getRequestDispatcher("/view/admin/admin-catalog-jsp.jsp").forward(request, response);
    }

    private boolean performAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productID = request.getParameter("productID");
        String action = request.getParameter("action");

        if (action != null && productID != null) {

            if (action.equals("DELETE")) {
                productDAO.deleteProduct(Integer.parseInt(productID));
                response.sendRedirect("/admin-catalog");
                return true;
            }
        }

        return false;
    }
}
