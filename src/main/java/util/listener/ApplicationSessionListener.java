package util.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session listener.
 *
 * @author Vlad Pasichniuk
 *
 */

public class ApplicationSessionListener implements HttpSessionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        LOGGER.info("User '{}' logged out from his account", httpSessionEvent.getSession().getAttribute("userLogin"));
        httpSessionEvent.getSession().setAttribute("cartProducts", null);
    }
}