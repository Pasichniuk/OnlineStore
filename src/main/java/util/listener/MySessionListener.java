package util.listener;

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
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("cartProducts", null);
    }
}
