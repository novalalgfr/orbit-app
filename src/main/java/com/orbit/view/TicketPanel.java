package com.orbit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.formdev.flatlaf.FlatClientProperties;

public class TicketPanel extends JPanel {

    // Callback untuk kembali ke halaman project
    private Runnable onBackAction;

    public TicketPanel(Runnable onBackAction) {
        this.onBackAction = onBackAction;
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); 
        setBorder(new EmptyBorder(30, 30, 30, 30)); 

        initComponents();
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); 

        // KIRI: Tombol Back & Judul Project
        JPanel leftContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftContainer.setOpaque(false);

        JButton btnBack = new JButton("← Back");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBack.setForeground(new Color(100, 100, 100));
        btnBack.setBackground(new Color(230, 230, 230));
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        btnBack.setPreferredSize(new Dimension(80, 30));
        btnBack.addActionListener(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(0, 15, 0, 0)); // Jarak dari tombol back

        JLabel title = new JLabel("Aplikasi E-Commerce"); // Nanti ini dinamis
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 33, 33));
        
        JLabel subtitle = new JLabel("Project ID: #1 • Tickets Overview");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        
        textPanel.add(title);
        textPanel.add(subtitle);

        leftContainer.add(btnBack);
        leftContainer.add(textPanel);

        // KANAN: Tombol New Ticket
        JButton btnAdd = new JButton("+ New Ticket");
        btnAdd.setBackground(new Color(79, 70, 229)); 
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setFocusPainted(false);
        btnAdd.putClientProperty(FlatClientProperties.STYLE, "arc: 10"); 
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.setPreferredSize(new Dimension(130, 32)); 

        btnAdd.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new TicketFormDialog(parentFrame).setVisible(true);
        });

        headerPanel.add(leftContainer, BorderLayout.WEST);
        headerPanel.add(btnAdd, BorderLayout.EAST);

        JPanel tableContainer = createTicketTable();

        add(headerPanel, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);
    }

    private JPanel createTicketTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));

        String[] columns = {"ID", "Ticket Title", "Assignee", "Priority", "Status"};
        Object[][] data = {
            {"101", "Design Database Schema", "Budi Santoso", "HIGH", "DONE"},
            {"102", "Setup Spring Boot", "Siti Aminah", "MEDIUM", "IN_PROGRESS"},
            {"103", "Create Login Page", "Budi Santoso", "LOW", "TODO"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
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

        // Atur Lebar Kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(60); // ID
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Assignee
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Priority
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Status

        // Center Alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Priority
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Status
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}