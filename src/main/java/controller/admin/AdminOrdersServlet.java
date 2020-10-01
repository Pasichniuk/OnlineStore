package controller.admin;

import database.dao.OrderDAO;
import entity.Order;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrdersServlet", urlPatterns = "/admin-orders")
public class AdminOrdersServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminOrdersServlet.class);

    private final OrderDAO orderDAO;

    private List<Order> orders;

    private static final int RECORDS_PER_PAGE = 5;
    private final int ordersAmount;
    private int pageNumber = 1;
    private int pagesAmount;

    public AdminOrdersServlet() {
        orderDAO = new OrderDAO();
        orders = orderDAO.getAllOrders();
        ordersAmount = orders.size();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (changeOrderStatus(request, response))
            return;

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getOrdersOnPage();

        request.setAttribute("orders", orders);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher("view/admin/admin-orders-jsp.jsp").forward(request, response);
    }

    private boolean changeOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderID = request.getParameter("orderID");
        String status = request.getParameter("status");

        if (orderID != null && status != null) {
            orderDAO.updateOrderStatus(Integer.parseInt(orderID), status);
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' changed status of order with ID=" + orderID + " to " + status + "...");
            response.sendRedirect("/admin-orders");
            return true;
        }

        return false;
    }

    private void getOrdersOnPage() {
        orders = orderDAO.getOrdersOnPage((pageNumber-1)*RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        pagesAmount = (int) Math.ceil(ordersAmount * 1.0 / RECORDS_PER_PAGE);
    }
}
