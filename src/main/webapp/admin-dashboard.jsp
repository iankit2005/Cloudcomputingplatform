<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Admin Dashboard</title>

    <!-- 🔁 AUTO REFRESH (REAL-TIME FEEL) -->
    <meta http-equiv="refresh" content="10">

    <style>
        body {
            font-family: Arial;
            background: #f4f6f8;
            padding: 20px;
        }

        h1 {
            margin-bottom: 5px;
        }

        .subtitle {
            color: gray;
            font-size: 14px;
        }

        .grid {
            display: flex;
            gap: 20px;
            margin-top: 25px;
        }

        .card {
            background: white;
            padding: 20px;
            border-radius: 6px;
            flex: 1;
            text-align: center;
            box-shadow: 0 0 6px rgba(0,0,0,0.1);
        }

        .card h2 {
            color: #007bff;
            margin: 0;
            font-size: 28px;
        }

        .card p {
            margin-top: 8px;
            color: #555;
            font-weight: bold;
        }

        .nav {
            margin-top: 30px;
            padding-top: 15px;
            border-top: 1px solid #ddd;
        }

        .nav a {
            text-decoration: none;
            margin-right: 15px;
            font-weight: bold;
            color: #007bff;
        }

        .nav a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<h1>📊 Admin Dashboard</h1>
<p class="subtitle">
    Last updated: <%= new java.util.Date() %> (Auto refresh every 10 seconds)
</p>

<!-- ================= STAT CARDS ================= -->
<div class="grid">

    <div class="card">
        <h2>₹ ${totalRevenue}</h2>
        <p>Total Revenue</p>
    </div>

    <div class="card">
        <h2>${usageMinutes}</h2>
        <p>Total Usage Minutes</p>
    </div>

    <div class="card">
        <h2>${running}</h2>
        <p>Running Resources</p>
    </div>

    <div class="card">
        <h2>${stopped}</h2>
        <p>Stopped Resources</p>
    </div>

</div>

<!-- ================= NAVIGATION ================= -->
<div class="nav">
    <a href="<%= request.getContextPath() %>/admin/resources">🔧 Manage Resources</a>
    <a href="<%= request.getContextPath() %>/admin/billing">💳 Billing Report</a>
    <a href="logout">🚪 Logout</a>
</div>

</body>
</html>
