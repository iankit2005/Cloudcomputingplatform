package servlet;

import models.User;
import database.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("user") : null;

        // 🔒 ADMIN AUTH CHECK
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 📊 FETCH STATS FROM DB
        double totalRevenue = DatabaseManager.getTotalRevenue();
        int usageMinutes = DatabaseManager.getTotalUsageMinutes();
        int running = DatabaseManager.getRunningResourceCount();
        int stopped = DatabaseManager.getStoppedResourceCount();

        // 🔁 SET ATTRIBUTES FOR JSP
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("usageMinutes", usageMinutes);
        request.setAttribute("running", running);
        request.setAttribute("stopped", stopped);

        // 🚀 FORWARD TO DASHBOARD UI
        request.getRequestDispatcher("/admin-dashboard.jsp")
                .forward(request, response);
    }
}
