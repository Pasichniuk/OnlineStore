package util.listener;

import entity.Cart;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
