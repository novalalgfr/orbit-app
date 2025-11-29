package com.orbit.view;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TicketFormDialog extends JDialog {

    private JTextField txtTitle;
    private JComboBox<String> cmbPriority;
    private JComboBox<String> cmbStatus;
    private JComboBox<String> cmbAssignee;
    private JButton btnSave;
    private JButton btnCancel;

    public TicketFormDialog(Frame parent) {
        super(parent, "Create New Ticket", true);
        setSize(450, 480); 
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
        
        JLabel lblTitle = new JLabel("New Ticket");
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

        // Input: Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Ticket Title"), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0); 
        txtTitle = new JTextField();
        txtTitle.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtTitle.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtTitle, gbc);

        // Input: Priority & Status (Sebelah-sebelahan)
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel row2 = new JPanel(new GridLayout(1, 2, 15, 0));
        row2.setBackground(Color.WHITE);

        JPanel pnlPrio = new JPanel(new BorderLayout());
        pnlPrio.setBackground(Color.WHITE);
        pnlPrio.add(createLabel("Priority"), BorderLayout.NORTH);
        cmbPriority = new JComboBox<>(new String[]{"HIGH", "MEDIUM", "LOW"});
        cmbPriority.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        cmbPriority.setPreferredSize(new Dimension(0, 35));
        pnlPrio.add(cmbPriority, BorderLayout.CENTER);

        JPanel pnlStatus = new JPanel(new BorderLayout());
        pnlStatus.setBackground(Color.WHITE);
        pnlStatus.add(createLabel("Status"), BorderLayout.NORTH);
        cmbStatus = new JComboBox<>(new String[]{"TODO", "IN_PROGRESS", "DONE"});
        cmbStatus.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        cmbStatus.setPreferredSize(new Dimension(0, 35));
        pnlStatus.add(cmbStatus, BorderLayout.CENTER);

        row2.add(pnlPrio);
        row2.add(pnlStatus);
        formPanel.add(row2, gbc);

        // Input: Assignee
        gbc.gridy = 3;
        gbc.insets = new Insets(15, 0, 8, 0);
        formPanel.add(createLabel("Assign To"), gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        cmbAssignee = new JComboBox<>(new String[]{"Budi Santoso", "Siti Aminah", "Unassigned"});
        cmbAssignee.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        cmbAssignee.setPreferredSize(new Dimension(0, 35));
        formPanel.add(cmbAssignee, gbc);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));

        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        btnCancel.setPreferredSize(new Dimension(90, 32));
        btnCancel.addActionListener(e -> dispose());

        btnSave = new JButton("Create Ticket");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setBackground(new Color(79, 70, 229));
        btnSave.setForeground(Color.WHITE);
        btnSave.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        btnSave.setPreferredSize(new Dimension(110, 32));
        
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Ticket Created (Dummy)");
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