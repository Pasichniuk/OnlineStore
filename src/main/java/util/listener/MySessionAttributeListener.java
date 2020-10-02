package util.listener;

import entity.Cart;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Session attribute listener.
 *
 * @author Vlad Pasichniuk
 *
 */

public class MySessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (httpSessionBindingEvent.getName().equals("userLogin"))
            Cart.getInstance().clearCart();
    }
}
