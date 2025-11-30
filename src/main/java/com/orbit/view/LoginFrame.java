package com.orbit.view;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {
        initWindow();
        initComponents();
    }

    private void initWindow() {
        setTitle("Login - Orbit App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        JPanel loginCard = new JPanel();
        loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(new EmptyBorder(40, 40, 50, 40));
        loginCard.setPreferredSize(new Dimension(400, 480));
        loginCard.putClientProperty(FlatClientProperties.STYLE, "arc: 20"); 
        
        loginCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(40, 40, 50, 40)
        ));

        JLabel lblLogo = new JLabel("ORBIT");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblLogo.setForeground(new Color(30, 30, 46));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Project Management System");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(Color.GRAY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        gbc.gridy = 0;
        formPanel.add(createLabel("Username"), gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(0, 40));
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        formPanel.add(txtUsername, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        formPanel.add(createLabel("Password"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 20, 0);
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(0, 40));
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        formPanel.add(txtPassword, gbc);

        btnLogin = new JButton("Sign In");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(79, 70, 229));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setPreferredSize(new Dimension(130, 32)); 

        btnLogin.addActionListener(e -> {
            this.dispose(); 
            new MainFrame().setVisible(true);
        });

        loginCard.add(Box.createVerticalGlue());
        loginCard.add(lblLogo);
        loginCard.add(Box.createRigidArea(new Dimension(0, 5)));
        loginCard.add(lblSubtitle);
        loginCard.add(Box.createRigidArea(new Dimension(0, 30)));
        loginCard.add(formPanel);
        loginCard.add(Box.createRigidArea(new Dimension(0, 10)));
        loginCard.add(btnLogin);
        loginCard.add(Box.createVerticalGlue());

        add(loginCard);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(80, 80, 80));
        return lbl;
    }
}