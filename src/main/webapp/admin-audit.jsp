<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

<%
    List<String[]> logs = (List<String[]>) request.getAttribute("logs");
%>

<html>
<head>
    <title>Admin Audit Logs</title>
    <style>
        body { font-family: Arial; background: #f4f6f8; padding: 20px; }
        table { width:100%; background:white; border-collapse:collapse; }
        th, td { padding:10px; border-bottom:1px solid #ddd; }
        th { background:#eee; }
    </style>
</head>

<body>

<h2>🛡 Admin Audit Logs</h2>

<table>
    <tr>
        <th>Actor</th>
        <th>Action</th>
        <th>Time</th>
    </tr>

    <% for (String[] log : logs) { %>
    <tr>
        <td><%= log[0] %></td>
        <td><%= log[1] %></td>
        <td><%= log[2] %></td>
    </tr>
    <% } %>
</table>

</body>
</html>
