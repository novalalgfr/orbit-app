package com.orbit.view;

import com.orbit.controller.UserController;
import com.orbit.model.User;
import com.orbit.service.AuthService;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserListPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private UserController controller;

    public UserListPanel() {
        this.controller = new UserController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); 
        setBorder(new EmptyBorder(30, 30, 30, 30)); 

        initComponents();
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<User> users = controller.getAllUsers();
        
        for (User u : users) {
            Object[] row = {
                u.getId(),
                u.getFullName(),
                u.getUsername(),
                u.getRole()
            };
            tableModel.addRow(row);
        }
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); 

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        JLabel title = new JLabel("Team Members");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 33, 33));
        
        JLabel subtitle = new JLabel("Manage access and roles");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        textPanel.add(title);
        textPanel.add(subtitle);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonContainer.setOpaque(false);

        if (AuthService.isAdmin()) {
            
            JButton btnDelete = new JButton("Delete");
            btnDelete.setBackground(Color.WHITE);
            btnDelete.setForeground(new Color(220, 38, 38));
            btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnDelete.setFocusPainted(false);
            btnDelete.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
            btnDelete.setPreferredSize(new Dimension(100, 32));
            
            btnDelete.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Select a user to delete!");
                    return;
                }

                int userId = (int) table.getValueAt(selectedRow, 0);
                String userName = (String) table.getValueAt(selectedRow, 1);
                
                if (userId == AuthService.getCurrentUser().getId()) {
                    JOptionPane.showMessageDialog(this, "You cannot delete your own account!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete user: " + userName + "?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (controller.deleteUser(userId)) {
                        loadData();
                    }
                }
            });

            JButton btnEdit = new JButton("Edit");
            btnEdit.setBackground(Color.WHITE);
            btnEdit.setForeground(new Color(50, 50, 50));
            btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnEdit.setFocusPainted(false);
            btnEdit.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
            btnEdit.setPreferredSize(new Dimension(80, 32));

            btnEdit.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Select a user to edit!");
                    return;
                }
                
                int id = (int) table.getValueAt(selectedRow, 0);
                String name = (String) table.getValueAt(selectedRow, 1);
                String uname = (String) table.getValueAt(selectedRow, 2);
                String role = (String) table.getValueAt(selectedRow, 3);
                
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
                new UserFormDialog(parent, id, name, uname, role).setVisible(true);
                loadData();
            });

            JButton btnAdd = new JButton("+ Add Member");
            btnAdd.setBackground(new Color(79, 70, 229)); 
            btnAdd.setForeground(Color.WHITE);
            btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnAdd.setFocusPainted(false);
            btnAdd.putClientProperty(FlatClientProperties.STYLE, "arc: 10"); 
            btnAdd.setPreferredSize(new Dimension(130, 32)); 

            btnAdd.addActionListener(e -> {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                new UserFormDialog(parentFrame).setVisible(true);
                loadData();
            });

            buttonContainer.add(btnDelete);
            buttonContainer.add(btnEdit);
            buttonContainer.add(btnAdd);
        }

        headerPanel.add(textPanel, BorderLayout.WEST);
        headerPanel.add(buttonContainer, BorderLayout.EAST);

        JPanel tableContainer = createUserTable();

        add(headerPanel, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);
    }

    private JPanel createUserTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));

        String[] columns = {"ID", "Full Name", "Username", "Role"};
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40); 
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 220, 220));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(100, 100, 100));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}