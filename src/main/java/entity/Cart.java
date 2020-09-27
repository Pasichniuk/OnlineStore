package entity;

import java.util.ArrayList;
import java.util.List;

public final class Cart {

    private static List<Product> products = new ArrayList<>();
    private static float totalPrice = 0.0f;

    public static void clearCart() {
        products = new ArrayList<>();
        totalPrice = 0.0f;
    }

    public static void increaseTotalPrice(float number) {
        totalPrice += number;
    }

    public static void decreaseTotalPrice(float number) {
        totalPrice -= number;
    }

    public static float getTotalPrice() {
        return totalPrice;
    }

    public static List<Product> getCartProducts() {
        return products;
    }

    public static List<Product> getCartProductsOnPage(int offset, int recordsPerPage) {
        List<Product> products = getCartProducts();
        List<Product> productsOnPage = new ArrayList<>();

        for (int i = offset; i < (offset + recordsPerPage); i++) {

            if (i < products.size())
                productsOnPage.add(products.get(i));
        }

        return productsOnPage;
    }
}
