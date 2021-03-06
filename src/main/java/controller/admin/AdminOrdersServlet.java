package controller.admin;

import constant.Constants;
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

/**
 * Admin Orders servlet controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminOrdersServlet", urlPatterns = Constants.PATH_ADMIN_ORDERS)
public class AdminOrdersServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AdminOrdersServlet.class);

    private final OrderDAO orderDAO;

    private List<Order> orders;

    private final int ordersAmount;

    private int pageNumber = 1;

    private int pagesAmount;

    public AdminOrdersServlet() {
        orderDAO = new OrderDAO();
        orders = orderDAO.getAllOrders();
        ordersAmount = orders.size();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (changeOrderStatus(request, response))
            return;

        if (request.getParameter("page") != null)
            pageNumber = Integer.parseInt(request.getParameter("page"));

        getOrdersOnPage();

        request.setAttribute("orders", orders);
        request.setAttribute("pagesAmount", pagesAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_ADMIN_ORDERS_JSP).forward(request, response);
    }

    /**
     * Changes status of order.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether order status is changed.
     *
     * @throws IOException If redirect failed.
     */
    private boolean changeOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderID = request.getParameter("orderID");
        String status = request.getParameter("status");

        if (orderID != null && status != null) {
            orderDAO.updateOrderStatus(Integer.parseInt(orderID), status);
            logger.info("Admin '" + request.getSession().getAttribute("userLogin") + "' changed status of order with ID=" + orderID + " to " + status + "...");
            response.sendRedirect(Constants.PATH_ADMIN_ORDERS);
            return true;
        }

        return false;
    }

    private void getOrdersOnPage() {
        orders = orderDAO.getOrdersOnPage((pageNumber-1) * Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);

        pagesAmount = (int) Math.ceil(ordersAmount * 1.0 / Constants.RECORDS_PER_PAGE);
    }
}
