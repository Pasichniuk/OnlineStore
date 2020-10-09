package util.listener;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener.
 *
 * @author Vlad Pasichniuk
 *
 */

public class MySessionListener implements HttpSessionListener {

    private static final Logger logger = Logger.getLogger(MySessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("User '" + httpSessionEvent.getSession().getAttribute("userLogin") + "' logged out from his account...");
        httpSessionEvent.getSession().setAttribute("cartProducts", null);
    }
}
