<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="service.UserService" %>

<%
    // 🔒 Disable cache (security)
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // 🔒 SESSION CHECK
    User admin = (User) session.getAttribute("user");

    if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserService userService = new UserService();
    List<User> users = userService.getAllUsers();
%>

<html>
<head>
    <title>Admin Dashboard | Cloud Platform</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
        }
        .navbar {
            background: #007bff;
            color: white;
            padding: 15px;
            font-size: 18px;
        }
        .logout {
            float: right;
            color: white;
            text-decoration: none;
            font-weight: bold;
        }
        .logout:hover {
            text-decoration: underline;
        }
        .container {
            padding: 20px;
        }
        .card {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 6px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background: #f0f0f0;
        }
        .stats {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .stat-box {
            flex: 1;
            background: #ffffff;
            padding: 20px;
            border-radius: 6px;
            text-align: center;
            box-shadow: 0 0 6px rgba(0,0,0,0.1);
        }
        .stat-box h2 {
            margin: 0;
            color: #007bff;
        }
        .empty {
            text-align: center;
            color: #888;
            padding: 15px;
        }
        .btn {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 15px;
            background: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        .btn:hover {
            background: #218838;
        }
    </style>
</head>
<body>

<div class="navbar">
    ☁ Cloud Computing Platform — Admin Dashboard
    <a class="logout" href="logout">Logout</a>
</div>

<div class="container">

    <!-- 📊 OVERVIEW -->
    <div class="stats">
        <div class="stat-box">
            <h2><%= users.size() %></h2>
            <p>Total Users</p>
        </div>
        <div class="stat-box">
            <h2>Operational</h2>
            <p>System Status</p>
        </div>
        <div class="stat-box">
            <h2><%= admin.getEmail() %></h2>
            <p>Logged-in Admin</p>
        </div>
    </div>

    <!-- ⚙️ ADMIN ACTIONS -->
    <div class="card">
        <h3>Admin Controls</h3>
        <a class="btn" href="admin/resources">Manage Cloud Resources</a>
    </div>

    <!-- 👥 USERS TABLE -->
    <div class="card">
        <h3>All Registered Users</h3>

        <% if (users.isEmpty()) { %>
        <div class="empty">No users found in the system.</div>
        <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
            </tr>

            <% for (User u : users) { %>
            <tr>
                <td><%= u.getId() %></td>
                <td><%= u.getName() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getRole() %></td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>

</div>
<a href="admin/billing">📊 View Billing Report</a>
<a href="admin/audit">🛡 Audit Logs</a>

</body>
</html>
