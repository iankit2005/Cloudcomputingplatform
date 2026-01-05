package servlet;

import models.User;
import service.AdminBillingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/billing")
public class AdminBillingServlet extends HttpServlet {

    private final AdminBillingService service = new AdminBillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("user") : null;

        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        request.setAttribute("report", service.getReport());
        request.setAttribute("totalRevenue", service.getTotalRevenue());
        request.setAttribute("usageMinutes", service.getTotalUsageMinutes());

        request.getRequestDispatcher("/admin-billing.jsp")
                .forward(request, response);
    }
}
