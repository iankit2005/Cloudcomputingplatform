<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="models.Resource, java.util.List" %>

<%
    // 🔒 Disable cache
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    List<Resource> vmResources =
            (List<Resource>) request.getAttribute("vmResources");

    List<Resource> dbResources =
            (List<Resource>) request.getAttribute("dbResources");

    // 🔔 Validation messages
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<html>
<head>
    <title>Admin Resource Monitoring</title>
    <style>
        body { font-family: Arial; background: #f4f6f8; padding: 20px; }
        h2 { margin-top: 30px; }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            margin-bottom: 30px;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }
        th { background: #eee; }

        .RUNNING { color: green; font-weight: bold; }
        .STOPPED { color: red; font-weight: bold; }

        button {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
        }
        .start { background: #28a745; color: white; }
        .stop { background: #dc3545; color: white; }

        .msg-error {
            color: red;
            font-weight: bold;
            margin-bottom: 15px;
        }
        .msg-success {
            color: green;
            font-weight: bold;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<h1>☁ Admin – Resource Monitoring</h1>

<!-- 🔔 VALIDATION / STATUS MESSAGES -->
<% if ("missing".equals(error)) { %>
<div class="msg-error">❌ Missing request parameters</div>
<% } else if ("invalidid".equals(error)) { %>
<div class="msg-error">❌ Invalid Resource ID</div>
<% } else if ("invalidaction".equals(error)) { %>
<div class="msg-error">❌ Invalid Action Requested</div>
<% } %>

<% if ("1".equals(success)) { %>
<div class="msg-success">✅ Action submitted successfully</div>
<% } %>

<!-- ================= VM RESOURCES ================= -->
<h2>🖥 Virtual Machines</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <% for (Resource r : vmResources) { %>
    <tr>
        <td><%= r.getId() %></td>
        <td><%= r.getName() %></td>
        <td class="<%= r.getStatus() %>">
            <%= r.getStatus() %>
        </td>
        <td>
            <form method="post"
                  action="<%= request.getContextPath() %>/admin/resources">
                <input type="hidden" name="resourceId" value="<%= r.getId() %>" />
                <% if ("RUNNING".equalsIgnoreCase(r.getStatus())) { %>
                <button class="stop" name="action" value="STOP">STOP</button>
                <% } else { %>
                <button class="start" name="action" value="START">START</button>
                <% } %>
            </form>
        </td>
    </tr>
    <% } %>
</table>

<!-- ================= DATABASE RESOURCES ================= -->
<h2>🗄 Databases</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <% for (Resource r : dbResources) { %>
    <tr>
        <td><%= r.getId() %></td>
        <td><%= r.getName() %></td>
        <td class="<%= r.getStatus() %>">
            <%= r.getStatus() %>
        </td>
        <td>
            <form method="post"
                  action="<%= request.getContextPath() %>/admin/resources">
                <input type="hidden" name="resourceId" value="<%= r.getId() %>" />
                <% if ("RUNNING".equalsIgnoreCase(r.getStatus())) { %>
                <button class="stop" name="action" value="STOP">STOP</button>
                <% } else { %>
                <button class="start" name="action" value="START">START</button>
                <% } %>
            </form>
        </td>
    </tr>
    <% } %>
</table>

<!-- 🔗 ADMIN BILLING -->
<a href="<%= request.getContextPath() %>/admin/billing">
    📊 View Billing Report
</a>

</body>
</html>
