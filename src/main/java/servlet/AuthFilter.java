package servlet;

import models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {
        "/adminDashboard.jsp",
        "/userDashboard.jsp",
        "/users",
        "/resources"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        // 🔒 Not logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        String uri = request.getRequestURI();

        // 🔒 Admin page protection
        if (uri.contains("admin") && !"ADMIN".equalsIgnoreCase(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 🔒 User page protection
        if (uri.contains("userDashboard") && !"USER".equalsIgnoreCase(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ✅ Allowed
        chain.doFilter(req, res);
    }
}
