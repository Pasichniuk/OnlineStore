package entityTest;

import entity.Order;
import org.junit.*;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void testOrderEntity() {
        Order order = new Order(1, 1, "PAID");

        assertNotNull(order);

        assertEquals(order.getOrderID(), 1);
        assertEquals(order.getUserID(), 1);
        assertEquals(order.getStatus(), "PAID");

        order.setOrderID(2);
        order.setUserID(2);
        order.setStatus("CANCELLED");

        assertEquals(order.getOrderID(), 2);
        assertEquals(order.getUserID(), 2);
        assertEquals(order.getStatus(), "CANCELLED");
    }
}
