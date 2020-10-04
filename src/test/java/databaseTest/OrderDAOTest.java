package databaseTest;

import database.dao.OrderDAO;

import java.sql.*;
import java.util.*;

import entity.Order;
import org.junit.*;

import static org.mockito.Mockito.*;

public class OrderDAOTest {

    @Test
    public void testOrderDAO() throws SQLException {
        final OrderDAO orderDAO = mock(OrderDAO.class);

        final Connection connection = mock(Connection.class);
        final Statement statement = mock(Statement.class);
        List<Order> orders = new ArrayList<>();

        when(connection.createStatement()).thenReturn(statement);
        when(orderDAO.getOrdersOnPage(0, 5)).thenReturn(orders);

        orderDAO.getOrdersOnPage(0, 5);

        verify(orderDAO).getOrdersOnPage(0, 5);

        //verify(connection).createStatement();
    }
}
