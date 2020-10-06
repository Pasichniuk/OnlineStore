package util.filter;

import constant.Constants;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Security filter for User.
 *
 * @author Vlad Pasichniuk
 *
 */

public class UserAuthenticationFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (filterConfig.getInitParameter("active").equalsIgnoreCase("true")) {

            final HttpServletRequest request = (HttpServletRequest) servletRequest;
            final HttpServletResponse response = (HttpServletResponse) servletResponse;

            final HttpSession session = request.getSession();

            if (session != null && session.getAttribute("role") != null) {

                if (session.getAttribute("role").equals(Constants.ROLE_ADMIN)) {
                    response.getWriter().write(notifyAccessDenied());
                    return;
                }
            }

            filterChain.doFilter(servletRequest, response);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

    private String notifyAccessDenied() {
        return "<script>" + "alert('Access denied!');" + "window.location = 'http://localhost:8080/log-in';" + "</script>";
    }
}
