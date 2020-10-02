package util.filter;

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

            final HttpServletRequest req = (HttpServletRequest) servletRequest;
            final HttpServletResponse resp = (HttpServletResponse) servletResponse;

            final HttpSession session = req.getSession();

            if (session != null && session.getAttribute("role") != null) {

                if (session.getAttribute("role").equals("ROLE_ADMIN")) {
                    resp.sendRedirect("/log-in");
                    return;
                }
            }

            filterChain.doFilter(servletRequest, resp);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
