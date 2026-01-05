package servlet;

import models.Resource;
import service.ResourceService;
import utils.InputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/resources")
public class ResourceServlet extends HttpServlet {

    private final ResourceService resourceService = new ResourceService();

    // ================== GET ==================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String userIdParam = request.getParameter("userId");

        // ❌ Missing userId
        if (InputValidator.isEmpty(userIdParam)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "userId required");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid userId");
            return;
        }

        List<Resource> resources =
                resourceService.getResourcesByUser(userId);

        StringBuilder sb = new StringBuilder();
        for (Resource r : resources) {
            sb.append(r.getId()).append(" | ")
                    .append(r.getName()).append(" | ")
                    .append(r.getType()).append(" | ")
                    .append(r.getStatus()).append("\n");
        }

        response.getWriter().write(sb.toString());
    }

    // ================== POST ==================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String configuration = request.getParameter("configuration");

        // ❌ Missing fields
        if (InputValidator.isEmpty(userIdParam)
                || InputValidator.isEmpty(name)
                || InputValidator.isEmpty(type)) {

            response.sendRedirect("userDashboard.jsp?error=missing");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("userDashboard.jsp?error=userid");
            return;
        }

        if (configuration == null) {
            configuration = "";
        }

        try {
            // 📦 Create resource
            Resource resource = new Resource();
            resource.setUserId(userId);
            resource.setName(name.trim());
            resource.setType(type);
            resource.setConfiguration(configuration.trim());
            resource.setStatus("RUNNING");

            boolean success = resourceService.addResource(resource);

            if (success) {
                response.sendRedirect("userDashboard.jsp?success=1");
            } else {
                response.sendRedirect("userDashboard.jsp?error=db");
            }

        } catch (Exception e) {
            response.sendRedirect("userDashboard.jsp?error=server");
        }
    }
}
