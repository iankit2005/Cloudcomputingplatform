package ui;

import models.User;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame implements Dashboard {

    private final User loggedInUser;

    public UserDashboard(User user) {
        this.loggedInUser = user;
        initUI();
    }

    @Override
    public void loadTabs() {
        // reserved for future
    }

    private void initUI() {
        setTitle("User Dashboard - " + loggedInUser.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("My Resources", createPlaceholder("View & manage deployed resources"));
        tabs.addTab("Monitoring", createPlaceholder("Monitoring features coming soon"));
        tabs.addTab("Support", createPlaceholder("Create & track support requests"));
        tabs.addTab("Billing", createPlaceholder("Usage reports & invoices"));
        tabs.addTab("Profile", createPlaceholder("Edit your profile"));

        add(tabs, BorderLayout.CENTER);

        JLabel footer = new JLabel("Logged in as User: " + loggedInUser.getEmail(), SwingConstants.RIGHT);
        footer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createPlaceholder(String txt) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(txt, SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }
}
