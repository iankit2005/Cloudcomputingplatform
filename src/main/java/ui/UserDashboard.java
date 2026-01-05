package ui;

import models.Resource;
import models.User;
import service.ResourceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class UserDashboard extends JFrame implements Dashboard {

    private final User loggedInUser;
    private final ResourceService resourceService = new ResourceService();

    private JTable resourceTable;
    private JLabel lblWelcome;
    private JLabel lblTotalResources;
    private JLabel monitoringLabel;

    public UserDashboard(User user) {
        this.loggedInUser = user;
        initUI();
        loadResources();
    }

    @Override
    public void loadTabs() {
        // handled in initUI
    }

    // ================= UI SETUP =================
    private void initUI() {

        setTitle("Cloud Platform | User Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- TOP PANEL --------
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblWelcome = createCard("Welcome User", loggedInUser.getName());
        lblTotalResources = createCard("My Resources", "Loading...");
        JLabel lblEmail = createCard("Email", loggedInUser.getEmail());

        topPanel.add(lblWelcome);
        topPanel.add(lblTotalResources);
        topPanel.add(lblEmail);

        add(topPanel, BorderLayout.NORTH);

        // -------- TABS --------
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("My Resources", createResourcesTab());
        tabs.addTab("Monitoring", createMonitoringTab());
        tabs.addTab("Billing", createPlaceholder("Billing summary will be shown here"));
        tabs.addTab("Support", createPlaceholder("Support ticket history"));

        add(tabs, BorderLayout.CENTER);
    }

    // ================= RESOURCES TAB =================
    private JPanel createResourcesTab() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resourceTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resourceTable);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void loadResources() {

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            List<Resource> resources;

            @Override
            protected Void doInBackground() {
                resources = resourceService.getResourcesByUser(loggedInUser.getId());
                return null;
            }

            @Override
            protected void done() {

                String[] cols = {"ID", "Name", "Type", "Configuration", "Status"};
                DefaultTableModel model = new DefaultTableModel(cols, 0);

                for (Resource r : resources) {
                    model.addRow(new Object[]{
                            r.getId(),
                            r.getName(),
                            r.getType(),
                            r.getConfiguration(),
                            r.getStatus()
                    });
                }

                resourceTable.setModel(model);

                // ⭐ INNOVATION: Status Color Badge
                resourceTable.getColumnModel()
                        .getColumn(4)   // Status column
                        .setCellRenderer(new StatusCellRenderer());

                lblTotalResources.setText("My Resources: " + resources.size());
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

        JButton refreshBtn = new JButton("Refresh Status");
        refreshBtn.addActionListener(e -> simulateMonitoring());

        panel.add(monitoringLabel, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void simulateMonitoring() {

        int cpu = (int) (Math.random() * 100);
        int ram = (int) (Math.random() * 100);

        monitoringLabel.setText(
                "CPU: " + cpu + "%   |   RAM: " + ram + "%"
        );
    }

    // ================= STATUS CELL RENDERER (INNOVATION) =================
    private static class StatusCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            String status = value.toString();

            if ("RUNNING".equalsIgnoreCase(status)) {
                c.setForeground(new Color(0, 128, 0)); // Green
            } else {
                c.setForeground(Color.RED); // Stopped / Others
            }

            return c;
        }
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
