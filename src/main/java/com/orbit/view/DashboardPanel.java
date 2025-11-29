package com.orbit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); 
        setBorder(new EmptyBorder(40, 40, 40, 40)); 

        initComponents();
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(33, 33, 33));
        
        JLabel subtitle = new JLabel("Welcome back, Admin!");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        
        headerPanel.add(title, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.CENTER);

        JPanel statsContainer = new JPanel(new GridLayout(1, 4, 25, 0)); 
        statsContainer.setOpaque(false);
        statsContainer.setPreferredSize(new Dimension(0, 140));

        statsContainer.add(createCard("Total Projects", "5", "active", new Color(79, 70, 229)));
        statsContainer.add(createCard("My Tasks", "12", "assigned", new Color(16, 185, 129)));
        statsContainer.add(createCard("Pending", "8", "urgent", new Color(245, 158, 11)));
        statsContainer.add(createCard("Completed", "104", "archived", new Color(99, 102, 241)));

        JPanel tablePanel = createRecentTable();

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.add(headerPanel, BorderLayout.NORTH);
        topSection.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.CENTER);
        topSection.add(statsContainer, BorderLayout.SOUTH);

        add(topSection, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value, String sub, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20"); 
        card.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(new Color(30, 30, 46));

        JPanel line = new JPanel();
        line.setBackground(accent);
        line.setPreferredSize(new Dimension(50, 4));
        line.putClientProperty(FlatClientProperties.STYLE, "arc: 4");

        JPanel content = new JPanel(new GridLayout(2, 1));
        content.setOpaque(false);
        content.add(lblTitle);
        content.add(lblValue);

        card.add(content, BorderLayout.CENTER);
        card.add(line, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createRecentTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitle = new JLabel("Recent Activities");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setBorder(new EmptyBorder(0, 0, 15, 0));

        String[] cols = {"ID", "Task Name", "Project", "Status", "Assignee"};
        Object[][] rows = {
            {"#101", "Design Login Page", "E-Commerce", "DONE", "Budi"},
            {"#102", "Setup Database", "E-Commerce", "DONE", "Siti"},
            {"#103", "Fix CSS Sidebar", "Orbit App", "IN_PROGRESS", "Admin"},
            {"#104", "Create Unit Test", "Orbit App", "TODO", "Admin"},
        };

        DefaultTableModel model = new DefaultTableModel(rows, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(40); 
        
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setIntercellSpacing(new Dimension(0, 1));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 249, 250));
        table.getTableHeader().setForeground(new Color(100, 100, 100));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(30, 0, 0, 0)); 
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        
        container.add(panel, BorderLayout.CENTER);
        return container;
    }
}