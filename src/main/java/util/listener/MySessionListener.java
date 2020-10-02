package util.listener;

import entity.Cart;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener.
 *
 * @author Vlad Pasichniuk
 *
 */

public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        Cart.clearCart();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Cart.clearCart();
    }
}
