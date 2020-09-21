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

    public static List<Product> getProducts() {
        return products;
    }

    public static float getTotalPrice() {
        return totalPrice;
    }
}
