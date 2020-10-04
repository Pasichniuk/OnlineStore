package entitytest;

import entity.Cart;
import entity.Product;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void getInstanceTest() {
        assertNotNull(Cart.getInstance());
    }

    @Test
    public void getCartProductsTest() {
        assertNotNull(Cart.getInstance().getCartProducts());
    }

    @Test
    public void clearCartTest() {
        Cart.getInstance().getCartProducts().add(new Product(1, "iPhone 5S", "Phones", 800.8f, new Date()));
        Cart.getInstance().getCartProducts().add(new Product(2, "Samsung Galaxy S4", "Phones", 900.9f, new Date()));

        Cart.getInstance().clearCart();

        List<Product> productList = new ArrayList<>();

        assertEquals(Cart.getInstance().getCartProducts(), productList);
    }

    @Test
    public void getProductsOnPageTest() {
        List<Product> products = Cart.getInstance().getCartProducts();

        products.add(new Product(1, "iPhone 5S", "Phones", 800.8f, new Date()));
        products.add(new Product(2, "Samsung Galaxy S4", "Phones", 900.9f, new Date()));

        assertEquals(products, Cart.getInstance().getCartProductsOnPage(0, 2));
    }
}
