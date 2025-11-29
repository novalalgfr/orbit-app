package com.orbit.view;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserFormDialog extends JDialog {

    private JTextField txtFullname;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnSave;
    private JButton btnCancel;

    public UserFormDialog(Frame parent) {
        super(parent, "Add Team Member", true);
        setSize(400, 480); 
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 25, 10, 25));
        
        JLabel lblTitle = new JLabel("Add Member");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(10, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 8, 0); 

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Full Name"), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0); 
        txtFullname = new JTextField();
        txtFullname.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtFullname.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtFullname, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(createLabel("Username"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtUsername.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtUsername, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(createLabel("Password"), gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtPassword.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtPassword, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(createLabel("Role"), gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        cmbRole = new JComboBox<>(new String[]{"MEMBER", "ADMIN"});
        cmbRole.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        cmbRole.setPreferredSize(new Dimension(0, 35));
        formPanel.add(cmbRole, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));

        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        btnCancel.setPreferredSize(new Dimension(90, 32));
        btnCancel.addActionListener(e -> dispose());

        btnSave = new JButton("Save User");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setBackground(new Color(79, 70, 229));
        btnSave.setForeground(Color.WHITE);
        btnSave.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        btnSave.setPreferredSize(new Dimension(100, 32));
        
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "User Created (Dummy)");
            dispose();
        });

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setBorder(new EmptyBorder(0, 0, 5, 0));
        return lbl;
    }
}