<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

<%
    List<String[]> bills =
            (List<String[]>) request.getAttribute("bills");
%>

<html>
<head>
    <title>User Billing</title>
    <style>
        body { font-family: Arial; background: #f4f6f8; padding: 20px; }
        table { width: 100%; background: white; border-collapse: collapse; }
        th, td { padding: 10px; border-bottom: 1px solid #ddd; }
        th { background: #eee; }
        .total { font-weight: bold; color: green; }
        .empty { color: #777; text-align: center; padding: 15px; }
    </style>
</head>

<body>

<h2>💳 Billing History</h2>

<% if (bills == null || bills.isEmpty()) { %>

<div class="empty">No billing records found.</div>

<% } else { %>

<table>
    <tr>
        <th>Resource</th>
        <th>Start</th>
        <th>End</th>
        <th>Minutes</th>
        <th>Cost (₹)</th>
    </tr>

    <% double total = 0;
        for (String[] b : bills) {
            total += Double.parseDouble(b[4]);
    %>
    <tr>
        <td><%= b[0] %></td>
        <td><%= b[1] %></td>
        <td><%= b[2] %></td>
        <td><%= b[3] %></td>
        <td>₹ <%= b[4] %></td>
    </tr>
    <% } %>

    <tr class="total">
        <td colspan="4">TOTAL</td>
        <td>₹ <%= total %></td>
    </tr>
</table>

<% } %>

</body>
</html>
