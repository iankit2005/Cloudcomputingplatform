package servlet;

import models.User;
import service.UserService;
import utils.InputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        List<User> users = userService.getAllUsers();

        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(u.getId()).append(" | ")
                    .append(u.getName()).append(" | ")
                    .append(u.getEmail()).append(" | ")
                    .append(u.getRole()).append("\n");
        }

        response.getWriter().write(sb.toString());
    }
}
