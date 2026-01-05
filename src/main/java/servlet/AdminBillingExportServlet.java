package servlet;

import models.User;
import service.AdminBillingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/billing/export")
public class AdminBillingExportServlet extends HttpServlet {

    private final AdminBillingService service = new AdminBillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("user") : null;

        // 🔒 ADMIN CHECK
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=admin_billing_report.csv");

        PrintWriter out = response.getWriter();

        // CSV HEADER
        out.println("Resource,User ID,Start Time,End Time,Minutes,Cost");

        List<String[]> report = service.getReport();

        for (String[] r : report) {
            out.println(String.join(",", r));
        }

        out.flush();
        out.close();
    }
}
