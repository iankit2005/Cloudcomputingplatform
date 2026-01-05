<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="models.User" %>
<%@ page import="models.Resource" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ResourceService" %>

<%
    // 🔒 Disable cache (security)
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // 🔒 SESSION CHECK
    User user = (User) session.getAttribute("user");

    if (user == null || !"USER".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }

    ResourceService resourceService = new ResourceService();
    List<Resource> resources = resourceService.getResourcesByUser(user.getId());

    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<html>
<head>
    <title>User Dashboard | Cloud Platform</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
        }
        .navbar {
            background: #28a745;
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
        .container {
            padding: 20px;
        }
        .msg {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            font-weight: bold;
        }
        .error { background: #ffe6e6; color: #c00; }
        .success { background: #e6ffea; color: #0a7a2f; }
        .stats {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .stat-box {
            flex: 1;
            background: white;
            padding: 20px;
            border-radius: 6px;
            text-align: center;
            box-shadow: 0 0 6px rgba(0,0,0,0.1);
        }
        .card {
            background: white;
            padding: 20px;
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
        .RUNNING { color: green; font-weight: bold; }
        .STOPPED { color: red; font-weight: bold; }
    </style>
</head>
<body>

<div class="navbar">
    ☁ Cloud Computing Platform — User Dashboard
    <a class="logout" href="logout">Logout</a>
</div>

<div class="container">

    <!-- ❌ ERROR MESSAGES -->
    <% if ("missing".equals(error)) { %>
    <div class="msg error">❌ All required fields must be filled</div>
    <% } else if ("userid".equals(error)) { %>
    <div class="msg error">❌ Invalid session. Please login again.</div>
    <% } else if ("db".equals(error)) { %>
    <div class="msg error">❌ Database error while deploying resource</div>
    <% } else if ("server".equals(error)) { %>
    <div class="msg error">❌ Server error occurred. Try again later.</div>
    <% } %>

    <!-- ✅ SUCCESS MESSAGE -->
    <% if ("1".equals(success)) { %>
    <div class="msg success">✅ Resource deployed successfully</div>
    <% } %>

    <!-- 📊 OVERVIEW -->
    <div class="stats">
        <div class="stat-box">
            <h2><%= user.getName() %></h2>
            <p>Welcome User</p>
        </div>
        <div class="stat-box">
            <h2><%= resources.size() %></h2>
            <p>My Resources</p>
        </div>
        <div class="stat-box">
            <h2><%= user.getEmail() %></h2>
            <p>Email</p>
        </div>
    </div>

    <!-- 🚀 DEPLOY FORM -->
    <form method="post"
          action="<%= request.getContextPath() %>/resources"
          onsubmit="return validateResourceForm()">

        <input type="hidden" name="userId" value="<%= user.getId() %>" />

        <label>Resource Name</label><br>
        <input type="text" id="name" name="name" placeholder="VM-101"><br><br>

        <label>Resource Type</label><br>
        <select id="type" name="type">
            <option value="">-- Select Type --</option>
            <option value="VM">Virtual Machine</option>
            <option value="DATABASE">Database</option>
        </select><br><br>

        <label>Configuration</label><br>
        <textarea id="config" name="configuration"></textarea><br><br>

        <button type="submit">🚀 Deploy Resource</button>
    </form>

    <!-- ☁ RESOURCE TABLE -->
    <div class="card">
        <h3>My Cloud Resources</h3>

        <% if (resources.isEmpty()) { %>
        <p>No cloud resources deployed yet.</p>
        <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Configuration</th>
                <th>Status</th>
            </tr>

            <% for (Resource r : resources) { %>
            <tr>
                <td><%= r.getId() %></td>
                <td><%= r.getName() %></td>
                <td><%= r.getType() %></td>
                <td><%= r.getConfiguration() %></td>
                <td class="<%= r.getStatus() %>">
                    <%= r.getStatus() %>
                </td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>

    <br>
    <a href="user/billing">📄 View My Billing</a>

</div>

<!-- 🧠 CLIENT SIDE VALIDATION -->
<script>
    function validateResourceForm() {
        let name = document.getElementById("name").value.trim();
        let type = document.getElementById("type").value;

        if (name === "") {
            alert("Resource name is required");
            return false;
        }
        if (name.length < 3) {
            alert("Resource name must be at least 3 characters");
            return false;
        }
        if (type === "") {
            alert("Please select resource type");
            return false;
        }
        return true;
    }
</script>

</body>
</html>
