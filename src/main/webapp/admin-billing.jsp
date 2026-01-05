<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>

<%
    List<String[]> report =
            (List<String[]>) request.getAttribute("report");
%>

<html>
<head>
    <title>Admin Billing Report</title>
    <style>
        body { font-family: Arial; background: #f4f6f8; padding: 20px; }
        table { width: 100%; background: white; border-collapse: collapse; }
        th, td { padding: 10px; border-bottom: 1px solid #ddd; }
        th { background: #eee; }
        .total { font-weight: bold; color: green; }
        .export-btn {
            padding: 8px 16px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-bottom: 15px;
        }
        .export-btn:hover {
            background: #0056b3;
        }
        .hint {
            color: #555;
            font-size: 14px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<h2>📊 Admin Billing Report</h2>
<div style="display:flex; gap:20px; margin-bottom:20px;">
    <div style="background:white;padding:15px;border-radius:6px;flex:1;">
        <b>Total Revenue:</b> ₹ ${totalRevenue}
    </div>
    <div style="background:white;padding:15px;border-radius:6px;flex:1;">
        <b>Total Usage Minutes:</b> ${usageMinutes}
    </div>
</div>

<!-- ✅ CSV EXPORT BUTTON (ADD THIS PART) -->
<a href="<%= request.getContextPath() %>/admin/billing/export">
    <button class="export-btn">⬇ Download CSV Report</button>
</a>

<div class="hint">
    * CSV can be opened in Excel / Google Sheets for accounting & audit.
</div>

<table>
    <tr>
        <th>Resource</th>
        <th>User ID</th>
        <th>Start</th>
        <th>End</th>
        <th>Minutes</th>
        <th>Cost (₹)</th>
    </tr>

    <% double total = 0;
        for (String[] r : report) {
            total += Double.parseDouble(r[5]);
    %>
    <tr>
        <td><%= r[0] %></td>
        <td><%= r[1] %></td>
        <td><%= r[2] %></td>
        <td><%= r[3] %></td>
        <td><%= r[4] %></td>
        <td>₹ <%= r[5] %></td>
    </tr>
    <% } %>

    <tr class="total">
        <td colspan="5">TOTAL REVENUE</td>
        <td>₹ <%= total %></td>
    </tr>
</table>

</body>
</html>
