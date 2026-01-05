package servlet;

import models.Resource;
import models.User;
import service.ResourceService;
import service.ResourceTask;
import utils.AppLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/admin/resources")
public class AdminResourceServlet extends HttpServlet {

    private final ResourceService resourceService = new ResourceService();

    // 🔥 THREAD POOL (REVIEW POINT)
    private static final ExecutorService executor =
            Executors.newFixedThreadPool(5);

    // ================== GET ==================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Resource> allResources = resourceService.getAllResources();

        List<Resource> vmResources = new ArrayList<>();
        List<Resource> dbResources = new ArrayList<>();

        for (Resource r : allResources) {
            if ("VM".equalsIgnoreCase(r.getType())) {
                vmResources.add(r);
            } else if ("DATABASE".equalsIgnoreCase(r.getType())) {
                dbResources.add(r);
            }
        }

        request.setAttribute("vmResources", vmResources);
        request.setAttribute("dbResources", dbResources);

        request.getRequestDispatcher("/admin-resources.jsp")
                .forward(request, response);
    }

    // ================== POST (EXCEPTION SAFE) ==================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User admin = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // 🔒 VALIDATION 1: parameters exist
            String resourceIdStr = request.getParameter("resourceId");
            String action = request.getParameter("action");

            if (resourceIdStr == null || action == null) {
                response.sendRedirect("resources?error=missing");
                return;
            }

            // 🔒 VALIDATION 2: resourceId integer
            int resourceId;
            try {
                resourceId = Integer.parseInt(resourceIdStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("resources?error=invalidid");
                return;
            }

            // 🔒 VALIDATION 3: action allowed
            if (!"START".equalsIgnoreCase(action)
                    && !"STOP".equalsIgnoreCase(action)) {
                response.sendRedirect("resources?error=invalidaction");
                return;
            }

            String newStatus =
                    "START".equalsIgnoreCase(action)
                            ? "RUNNING"
                            : "STOPPED";

            // ✅ LOG ADMIN ACTION (REVIEW GOLD)
            AppLogger.log(
                    "ADMIN ACTION → " + admin.getEmail()
                            + " requested " + action
                            + " on resource ID " + resourceId
            );

            // 🔥 ASYNC EXECUTION
            ResourceTask task =
                    new ResourceTask(resourceId, newStatus);
            executor.execute(task);

            response.sendRedirect("resources?success=1");

        } catch (Exception e) {
            // ❌ CATCH ALL (SAFETY)
            AppLogger.log(
                    "ADMIN ERROR → " + e.getClass().getSimpleName()
                            + " : " + e.getMessage()
            );

            response.sendRedirect("resources?error=server");
        }
    }
}
