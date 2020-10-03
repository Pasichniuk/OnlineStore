import entity.Product;
import util.Sorter;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class SorterTest {

    private final List<Product> products = new ArrayList<>();

    @Test
    public void sortProductsAZTest() {
        products.add(new Product(1, "iPhone 5S", "Phones", 800.8f, new Date()));
        products.add(new Product(2, "Samsung Galaxy S4", "Phones", 900.9f, new Date()));

        Sorter.sortProductsAZ(products);

        assertNotNull(products);
    }

    @Test
    public void sortProductsZATest() {
        products.add(new Product(3, "Xiaomi Redmi 4X", "Phones", 700.7f, new Date()));
        products.add(new Product(4, "iPhone 6S Plus", "Phones", 500.8f, new Date()));

        Sorter.sortProductsZA(products);
        assertNotNull(products);
    }

    @Test
    public void sortProductsCheapExpensiveTest() {
        products.add(new Product(5, "Xiaomi 9 Pro", "Phones", 200.9f, new Date()));
        products.add(new Product(6, "Airpods", "Headphones", 350.3f, new Date()));

        Sorter.sortProductsCheapExpensive(products);
        assertNotNull(products);
    }

    @Test
    public void sortProductsExpensiveCheapTest() {
        products.add(new Product(7, "Airpods Pro", "Headphones", 800.8f, new Date()));
        products.add(new Product(8, "iPhone X", "Phones", 1000.0f, new Date()));

        Sorter.sortProductsExpensiveCheap(products);
        assertNotNull(products);
    }

    @Test
    public void sortProductsOlderNewerTest() {
        products.add(new Product(9, "Homepod", "Home appliances", 1500.5f, new Date()));
        products.add(new Product(10, "Macbook Pro", "Laptops", 1200.2f, new Date()));

        Sorter.sortProductsOlderNewer(products);
        assertNotNull(products);
    }

    @Test
    public void sortProductsNewerOlderTest() {
        products.add(new Product(11, "Xiaomi Redmi 5", "Phones", 180.9f, new Date()));
        products.add(new Product(12, "iPhone XS", "Phones", 990.1f, new Date()));

        Sorter.sortProductsNewerOlder(products);
        assertNotNull(products);
    }

}
