package servlet;

import models.User;
import service.BillingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user/billing")
public class UserBillingServlet extends HttpServlet {

    private final BillingService service = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔒 Disable cache (important for security)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // 🔒 AUTH + ROLE CHECK
        if (user == null || !"USER".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 📊 Fetch user billing history
        request.setAttribute(
                "bills",
                service.getUserBilling(user.getId())
        );

        request.getRequestDispatcher("/user-billing.jsp")
                .forward(request, response);
    }
}
