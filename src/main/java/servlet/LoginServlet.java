package servlet;

import models.User;
import service.UserService;
import utils.InputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 1️⃣ Empty check
        if (InputValidator.isEmpty(email) || InputValidator.isEmpty(password)) {
            response.sendRedirect("login.jsp?error=" +
                    URLEncoder.encode("Email & Password required", "UTF-8"));
            return;
        }

        // 2️⃣ Email format check
        if (!InputValidator.isValidEmail(email)) {
            response.sendRedirect("login.jsp?error=" +
                    URLEncoder.encode("Invalid email format", "UTF-8"));
            return;
        }

        // 3️⃣ Authenticate from DB
        User user = userService.login(email, password);

        if (user == null) {
            response.sendRedirect("login.jsp?error=" +
                    URLEncoder.encode("Invalid email or password", "UTF-8"));
            return;
        }

        // 4️⃣ Session create
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);

        // 5️⃣ Role based redirect
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("adminDashboard.jsp");
        } else {
            response.sendRedirect("userDashboard.jsp");
        }
    }
}
