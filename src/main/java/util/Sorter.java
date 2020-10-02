package util;

import entity.Product;

import java.util.Comparator;
import java.util.List;

/**
 * Provides methods for sorting list of products
 *
 * @author Vlad Pasichniuk
 *
 */

public class Sorter {

    private Sorter() {}

    private static final Comparator<Product> SORT_PRODUCTS_A_Z = Comparator.comparing(Product::getName);

    private static final Comparator<Product> SORT_PRODUCTS_Z_A = Comparator.comparing(Product::getName).reversed();

    private static final Comparator<Product> SORT_PRODUCTS_CHEAP_EXPENSIVE = Comparator.comparing(Product::getPrice);

    private static final Comparator<Product> SORT_PRODUCTS_EXPENSIVE_CHEAP = Comparator.comparing(Product::getPrice).reversed();

    private static final Comparator<Product> SORT_PRODUCTS_OLDER_NEWER = Comparator.comparing(Product::getAdditionDate);

    private static final Comparator<Product> SORT_PRODUCTS_NEWER_OLDER = Comparator.comparing(Product::getAdditionDate).reversed();

    public static void sortProductsAZ(List<Product> products) {
        products.sort(SORT_PRODUCTS_A_Z);
    }

    public static void sortProductsZA(List<Product> products) {
        products.sort(SORT_PRODUCTS_Z_A);
    }

    public static void sortProductsCheapExpensive(List<Product> products) {
        products.sort(SORT_PRODUCTS_CHEAP_EXPENSIVE);
    }

    public static void sortProductsExpensiveCheap(List<Product> products) {
        products.sort(SORT_PRODUCTS_EXPENSIVE_CHEAP);
    }

    public static void sortProductsOlderNewer(List<Product> products) {
        products.sort(SORT_PRODUCTS_OLDER_NEWER);
    }

    public static void sortProductsNewerOlder(List<Product> products) {
        products.sort(SORT_PRODUCTS_NEWER_OLDER);
    }
}
