package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores products added to the cart.
 *
 * Singleton.
 *
 * @author Vlad Pasichniuk
 *
 */

public class Cart {

    private static Cart instance;

    private static List<Product> products = new ArrayList<>();

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null)
            instance = new Cart();

        return instance;
    }

    public void clearCart() {
        products = new ArrayList<>();
    }

    public List<Product> getCartProducts() {
        return products;
    }

    public List<Product> getCartProductsOnPage(int offset, int recordsPerPage) {
        List<Product> products = getCartProducts();
        List<Product> productsOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + recordsPerPage); i++) {

            if (i < products.size())
                productsOnPage.add(products.get(i));
        }

        return productsOnPage;
    }
}
