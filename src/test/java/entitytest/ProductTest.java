package entitytest;

import entity.Product;

import org.junit.*;
import java.util.Date;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testProductEntity() {
        Product product = new Product(1, "iPhone XR", "Phones", 800.8f, new Date());

        assertNotNull(product);

        product.setId(2);
        product.setName("iPhone 11");
        product.setCategory("Phones");
        product.setCategoryRU("Телефоны");
        product.setPrice(900.9f);
        product.setAdditionDate(new Date());

        assertEquals(product.getId(), 2);
        assertEquals(product.getName(), "iPhone 11");
        assertEquals(product.getCategory(), "Phones");
        assertEquals(product.getCategoryRU(), "Телефоны");
        assertEquals(product.getPrice(), 900.9f, 0.9);
        assertEquals(product.getAdditionDate(), new Date());
    }
}
