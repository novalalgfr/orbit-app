package com.orbit.view;

import com.orbit.controller.ProjectController;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProjectFormDialog extends JDialog {

    private JTextField txtName;
    private JTextArea txtDescription;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JButton btnSave;
    private JButton btnCancel;
    
    // Tambahkan Controller
    private ProjectController controller;

    public ProjectFormDialog(Frame parent) {
        super(parent, "Create New Project", true);
        this.controller = new ProjectController(); // Init Controller
        
        setSize(450, 520); 
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
        
        JLabel lblTitle = new JLabel("New Project");
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
        formPanel.add(createLabel("Project Name"), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0); 
        txtName = new JTextField();
        txtName.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtName.setPreferredSize(new Dimension(0, 35));
        formPanel.add(txtName, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(createLabel("Description"), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtDescription = new JTextArea(4, 20);
        txtDescription.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        formPanel.add(scrollDesc, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel dateRow = new JPanel(new GridLayout(1, 2, 15, 0)); 
        dateRow.setBackground(Color.WHITE);

        JPanel pnlStart = new JPanel(new BorderLayout());
        pnlStart.setBackground(Color.WHITE);
        pnlStart.add(createLabel("Start Date (YYYY-MM-DD)"), BorderLayout.NORTH);
        txtStartDate = new JTextField();
        txtStartDate.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtStartDate.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "2023-11-01");
        txtStartDate.setPreferredSize(new Dimension(0, 35));
        pnlStart.add(txtStartDate, BorderLayout.CENTER);

        JPanel pnlEnd = new JPanel(new BorderLayout());
        pnlEnd.setBackground(Color.WHITE);
        pnlEnd.add(createLabel("End Date (YYYY-MM-DD)"), BorderLayout.NORTH);
        txtEndDate = new JTextField();
        txtEndDate.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        txtEndDate.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "2023-12-31");
        txtEndDate.setPreferredSize(new Dimension(0, 35));
        pnlEnd.add(txtEndDate, BorderLayout.CENTER);

        dateRow.add(pnlStart);
        dateRow.add(pnlEnd);
        formPanel.add(dateRow, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));

        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        btnCancel.setPreferredSize(new Dimension(90, 32));
        btnCancel.addActionListener(e -> dispose());

        btnSave = new JButton("Save Project");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSave.setBackground(new Color(79, 70, 229));
        btnSave.setForeground(Color.WHITE);
        btnSave.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        btnSave.setPreferredSize(new Dimension(110, 32));
        
        // --- LOGIC SAVE DATA ---
        btnSave.addActionListener(e -> {
            String name = txtName.getText();
            String desc = txtDescription.getText();
            String start = txtStartDate.getText();
            String end = txtEndDate.getText();

            // Panggil Controller
            boolean isSuccess = controller.addProject(name, desc, start, end);
            
            if (isSuccess) {
                dispose(); // Tutup dialog jika sukses
            }
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