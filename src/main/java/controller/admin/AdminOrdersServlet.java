package controller.admin;

import dao.OrderDAO;
import entity.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrdersServlet", urlPatterns = "/admin-orders")
public class AdminOrdersServlet extends HttpServlet {

    private final OrderDAO orderDAO;

    public AdminOrdersServlet() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (changeOrderStatus(request, response))
            return;

        List<Order> orders = orderDAO.getAllOrders();

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("view/admin/admin-orders-jsp.jsp").forward(request, response);
    }

    private boolean changeOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderID = request.getParameter("orderID");
        String status = request.getParameter("status");

        if (orderID != null && status != null) {
            orderDAO.updateOrderStatus(Integer.parseInt(orderID), status);
            response.sendRedirect("/admin-orders");
            return true;
        }

        return false;
    }
}
