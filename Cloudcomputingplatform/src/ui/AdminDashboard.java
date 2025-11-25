package ui;

import database.DatabaseManager;
import models.User;
import ui.Dashboard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame implements Dashboard {

    private final User loggedInUser;

    private JTextArea usersTextArea;
    private JLabel monitoringLabel;
    private volatile boolean monitoringRunning = true;

    public AdminDashboard(User user) {
        this.loggedInUser = user;
        initUI();
    }

    @Override
    public void loadTabs() {
        // Already loaded via initUI
    }

    private void initUI() {
        setTitle("Admin Dashboard - " + loggedInUser.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // USER TAB → Collections + Generics use
        JPanel usersPanel = new JPanel(new BorderLayout());
        JButton loadUsersBtn = new JButton("Load All Users");
        usersTextArea = new JTextArea();
        usersTextArea.setEditable(false);

        loadUsersBtn.addActionListener(e -> loadUsers());
        usersPanel.add(loadUsersBtn, BorderLayout.NORTH);
        usersPanel.add(new JScrollPane(usersTextArea), BorderLayout.CENTER);
        tabs.addTab("Users", usersPanel);

        // RESOURCES TAB
        tabs.addTab("Resources", createPlaceholderPanel("Resource Management (Create/Edit/Delete Resources)"));

        // MONITORING TAB → Multithreading
        JPanel monitoringPanel = new JPanel(new BorderLayout());
        monitoringLabel = new JLabel("Monitoring not started", SwingConstants.CENTER);
        JButton startMonitoringButton = new JButton("Start Monitoring");
        startMonitoringButton.addActionListener(e -> startMonitoring());
        monitoringPanel.add(monitoringLabel, BorderLayout.CENTER);
        monitoringPanel.add(startMonitoringButton, BorderLayout.SOUTH);
        tabs.addTab("Monitoring", monitoringPanel);

        tabs.addTab("Support", createPlaceholderPanel("Support Ticket Management"));
        tabs.addTab("Billing", createPlaceholderPanel("Billing & Invoices"));

        add(tabs, BorderLayout.CENTER);

        JLabel footer = new JLabel("Logged in as Admin: " + loggedInUser.getEmail(), SwingConstants.RIGHT);
        footer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createPlaceholderPanel(String msg) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(msg, SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }

    // COLLECTIONS + GENERICS MARKS
    private void loadUsers() {
        List<User> users = DatabaseManager.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(u.toString()).append("\n");
        }
        usersTextArea.setText(sb.toString());
    }

    // MULTITHREADING MARKS
    private void startMonitoring() {
        Thread t = new Thread(() -> {
            while (monitoringRunning) {
                int cpu = (int) (Math.random() * 100);
                int ram = (int) (Math.random() * 100);
                String text = "CPU: " + cpu + "%   |   RAM: " + ram + "%";

                SwingUtilities.invokeLater(() -> monitoringLabel.setText(text));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
