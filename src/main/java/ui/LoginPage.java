package ui;

import models.User;
import service.UserService;
import utils.InputValidator;
import utils.InvalidLoginException;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    // ✅ Service layer
    private final UserService userService = new UserService();

    public LoginPage() {
        initUI();
    }

    private void initUI() {
        setTitle("Cloud Platform - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 11f));

        bottomPanel.add(loginButton, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        panel.add(
                new JLabel("Cloud Computing Platform Login", SwingConstants.CENTER),
                BorderLayout.NORTH
        );
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    // ================= VALIDATED LOGIN =================
    private User doLogin(String email, String password) throws InvalidLoginException {

        // Empty check
        if (InputValidator.isEmpty(email) || InputValidator.isEmpty(password)) {
            throw new InvalidLoginException("Email and Password cannot be empty.");
        }

        // Email format validation
        if (!InputValidator.isValidEmail(email)) {
            throw new InvalidLoginException("Please enter a valid email address.");
        }

        // Password validation
        if (!InputValidator.isValidPassword(password)) {
            throw new InvalidLoginException("Password must be at least 5 characters long.");
        }

        // Service layer authentication
        User user = userService.login(email, password);
        if (user == null) {
            throw new InvalidLoginException("Invalid email or password.");
        }

        return user;
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        try {
            User user = doLogin(email, password);

            statusLabel.setForeground(new Color(0, 128, 0));
            statusLabel.setText("Login successful: " + user.getRole());

            Dashboard dashboard;
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                dashboard = new AdminDashboard(user);
            } else {
                dashboard = new UserDashboard(user);
            }

            ((JFrame) dashboard).setVisible(true);
            dispose();

        } catch (InvalidLoginException ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Unexpected error occurred.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
