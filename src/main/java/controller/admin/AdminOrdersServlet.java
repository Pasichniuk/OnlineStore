package controller.admin;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.dao.OrderDAO;
import constant.Constants;

/**
 * Admin order controller.
 *
 * @author Vlad Pasichniuk.
 *
 */

@WebServlet(name = "AdminOrdersServlet", urlPatterns = Constants.PATH_ADMIN_ORDERS)
public class AdminOrdersServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminOrdersServlet.class);

    private final OrderDAO orderDAO;

    public AdminOrdersServlet() {
        this.orderDAO = new OrderDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (changeOrderStatus(request, response)) {
            return;
        }

        var pageNumber = 1;

        if (request.getParameter("page") != null) {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }

        var orders = orderDAO.getOrdersOnPage(
            (pageNumber - 1) * Constants.RECORDS_PER_PAGE,
            Constants.RECORDS_PER_PAGE
        );

        var pageAmount = (int) Math.ceil(orders.size() * 1.0 / Constants.RECORDS_PER_PAGE);

        request.setAttribute("orders", orders);
        request.setAttribute("pagesAmount", pageAmount);
        request.setAttribute("currentPage", pageNumber);

        request.getRequestDispatcher(Constants.PATH_ADMIN_ORDERS_JSP).forward(request, response);
    }

    /**
     * Changes a status of an order.
     *
     * @param request Request.
     * @param response Response.
     *
     * @return Whether order status is changed.
     *
     * @throws IOException If redirect failed.
     */
    private boolean changeOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var orderID = request.getParameter("orderID");
        var status = request.getParameter("status");

        if (orderID != null && status != null) {
            orderDAO.updateOrderStatus(Integer.parseInt(orderID), status);
            LOGGER.info("Admin '{}' changed status of order with ID={} to {}", request.getSession().getAttribute("userLogin"), orderID, status);
            response.sendRedirect(Constants.PATH_ADMIN_ORDERS);
            return true;
        }

        return false;
    }
}