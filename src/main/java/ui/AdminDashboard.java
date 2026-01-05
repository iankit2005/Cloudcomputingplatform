package ui;

import models.User;
import service.UserService;
import database.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame implements Dashboard {

    private final User loggedInUser;
    private final UserService userService = new UserService();

    // UI Components
    private JTable usersTable;
    private JLabel lblTotalUsers;
    private JLabel lblSystemStatus;
    private JLabel monitoringLabel;

    // Monitoring control
    private volatile boolean monitoringRunning = false;
    private Thread monitoringThread;

    // Auto refresh stats thread
    private Thread statsThread;

    public AdminDashboard(User user) {
        this.loggedInUser = user;
        initUI();
        loadUsers();
        startAutoRefreshStats();   // ⭐ INNOVATION
    }

    @Override
    public void loadTabs() {
        // handled in initUI
    }

    // ================= UI SETUP =================
    private void initUI() {

        setTitle("Cloud Computing Platform | Admin Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- TOP OVERVIEW PANEL --------
        JPanel overviewPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        overviewPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblTotalUsers = createCard("Total Users", "Loading...");
        lblSystemStatus = createCard("System Status", "Operational");
        JLabel lblAdmin = createCard("Logged in Admin", loggedInUser.getEmail());

        overviewPanel.add(lblTotalUsers);
        overviewPanel.add(lblSystemStatus);
        overviewPanel.add(lblAdmin);

        add(overviewPanel, BorderLayout.NORTH);

        // -------- TABS --------
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Users", createUsersTab());
        tabs.addTab("Resources", createPlaceholder("Cloud Resources (VMs, Databases, Storage)"));
        tabs.addTab("Monitoring", createMonitoringTab());
        tabs.addTab("Support", createPlaceholder("Support Tickets Management"));
        tabs.addTab("Billing", createPlaceholder("Invoices & Payments"));

        add(tabs, BorderLayout.CENTER);
    }

    // ================= USERS TAB =================
    private JPanel createUsersTab() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(usersTable);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void loadUsers() {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            List<User> users;

            @Override
            protected Void doInBackground() {
                users = userService.getAllUsers();
                return null;
            }

            @Override
            protected void done() {

                String[] columns = {"ID", "Name", "Email", "Role"};
                DefaultTableModel model = new DefaultTableModel(columns, 0);

                for (User u : users) {
                    model.addRow(new Object[]{
                            u.getId(),
                            u.getName(),
                            u.getEmail(),
                            u.getRole()
                    });
                }

                usersTable.setModel(model);
                lblTotalUsers.setText("Total Users: " + users.size());
            }
        };

        worker.execute();
    }

    // ================= MONITORING TAB =================
    private JPanel createMonitoringTab() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        monitoringLabel = new JLabel("CPU: --% | RAM: --%", SwingConstants.CENTER);
        monitoringLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel buttonPanel = new JPanel();
        JButton btnStart = new JButton("Start Monitoring");
        JButton btnStop = new JButton("Stop Monitoring");

        btnStart.addActionListener(e -> startMonitoring());
        btnStop.addActionListener(e -> stopMonitoring());

        buttonPanel.add(btnStart);
        buttonPanel.add(btnStop);

        panel.add(monitoringLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void startMonitoring() {

        if (monitoringRunning) return;

        monitoringRunning = true;

        monitoringThread = new Thread(() -> {
            while (monitoringRunning) {

                int cpu = (int) (Math.random() * 100);
                int ram = (int) (Math.random() * 100);

                SwingUtilities.invokeLater(() ->
                        monitoringLabel.setText(
                                "CPU: " + cpu + "%   |   RAM: " + ram + "%"
                        )
                );

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {}
            }
        });

        monitoringThread.setDaemon(true);
        monitoringThread.start();
    }

    private void stopMonitoring() {
        monitoringRunning = false;
        monitoringLabel.setText("Monitoring stopped");
    }

    // ================= INNOVATION =================
    // Auto-refresh statistics using background thread
    private void startAutoRefreshStats() {

        statsThread = new Thread(() -> {
            while (true) {
                try {
                    int totalUsers = userService.getAllUsers().size();

                    SwingUtilities.invokeLater(() -> {
                        lblTotalUsers.setText("Total Users: " + totalUsers);
                        lblSystemStatus.setText("System Status: Operational");
                    });

                    Thread.sleep(5000); // refresh every 5 sec

                } catch (Exception e) {
                    break;
                }
            }
        });

        statsThread.setDaemon(true);
        statsThread.start();
    }

    // ================= COMMON HELPERS =================
    private JLabel createCard(String title, String value) {

        JLabel lbl = new JLabel(
                "<html><center><b>" + title + "</b><br/>" + value + "</center></html>",
                SwingConstants.CENTER
        );

        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return lbl;
    }

    private JPanel createPlaceholder(String text) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(text, SwingConstants.CENTER), BorderLayout.CENTER);
        return panel;
    }
}
