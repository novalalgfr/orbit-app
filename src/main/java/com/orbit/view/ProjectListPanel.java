package com.orbit.view;

import com.orbit.controller.ProjectController;
import com.orbit.model.Project;
import com.orbit.service.AuthService; // <--- JANGAN LUPA IMPORT INI
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ProjectListPanel extends JPanel {
    
    // ... variable deklarasi (table, model, controller) tetep sama ...
    private JTable table;
    private DefaultTableModel tableModel;
    private ProjectController controller;

    public ProjectListPanel() {
        this.controller = new ProjectController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); 
        setBorder(new EmptyBorder(30, 30, 30, 30)); 

        initComponents();
        loadData();
    }

    // ... method loadData() tetep sama ...
    private void loadData() {
        tableModel.setRowCount(0);
        List<Project> projects = controller.getAllProjects();
        LocalDate today = LocalDate.now();

        for (Project p : projects) {
            String status = "Active";
            try {
                if (p.getEndDate() != null && !p.getEndDate().isEmpty()) {
                    LocalDate endDate = LocalDate.parse(p.getEndDate());
                    if (endDate.isBefore(today)) {
                        status = "Completed";
                    }
                }
            } catch (DateTimeParseException e) { }

            Object[] row = {
                p.getId(),
                p.getProjectName(),
                p.getDescription(),
                p.getStartDate(),
                p.getEndDate(),
                status
            };
            tableModel.addRow(row);
        }
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); 

        // BAGIAN JUDUL (KIRI) - Tetap Sama
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        JLabel title = new JLabel("All Projects");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 33, 33));
        
        JLabel subtitle = new JLabel("Manage your projects");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        textPanel.add(title);
        textPanel.add(subtitle);

        // BAGIAN TOMBOL (KANAN)
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonContainer.setOpaque(false);

        // 1. Tombol OPEN (Tetap Sama - Semua orang boleh buka project)
        JButton btnOpen = new JButton("Open Project");
        btnOpen.setBackground(Color.WHITE);
        btnOpen.setForeground(new Color(50, 50, 50));
        btnOpen.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnOpen.setFocusPainted(false);
        btnOpen.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        btnOpen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOpen.setPreferredSize(new Dimension(120, 32));

        btnOpen.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a project first!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                // 1. Ambil ID dan Nama Project dari Tabel
                int projectId = (int) table.getValueAt(selectedRow, 0);
                String projectName = (String) table.getValueAt(selectedRow, 1); 
                
                // 2. Cari Parent Container (ContentPanel di MainFrame)
                Container parent = SwingUtilities.getAncestorOfClass(JPanel.class, this);
                
                if (parent != null) {
                    // 3. Cari TicketPanel di dalam daftar komponen parent
                    // Kita harus looping mencari komponen mana yang merupakan TicketPanel
                    for (Component comp : parent.getComponents()) {
                        if (comp instanceof TicketPanel) {
                            TicketPanel ticketPanel = (TicketPanel) comp;
                            
                            // Kirim Nama Project untuk Judul Header
                            ticketPanel.setProjectHeader(projectName);
                            
                            // Kirim ID Project untuk ambil data tiket dari DB
                            ticketPanel.loadData(projectId);
                            
                            break; // Stop looping kalau sudah ketemu
                        }
                    }

                    // 4. Pindah Layar ke TICKETS
                    CardLayout cl = (CardLayout) parent.getLayout();
                    cl.show(parent, "TICKETS");
                }
            }
        });

        buttonContainer.add(btnOpen);

        // 2. Tombol NEW PROJECT (HANYA ADMIN) 🔒
        // Kita cek: Apakah user yang login adalah ADMIN?
        if (AuthService.isAdmin()) {
            JButton btnAdd = new JButton("+ New Project");
            btnAdd.setBackground(new Color(79, 70, 229)); 
            btnAdd.setForeground(Color.WHITE);
            btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btnAdd.setFocusPainted(false);
            btnAdd.putClientProperty(FlatClientProperties.STYLE, "arc: 10"); 
            btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnAdd.setPreferredSize(new Dimension(130, 32)); 

            btnAdd.addActionListener(e -> {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                ProjectFormDialog dialog = new ProjectFormDialog(parentFrame);
                dialog.setVisible(true); 
                loadData();
            });
            
            // Masukkan tombol Add HANYA jika Admin
            buttonContainer.add(btnAdd);
        }

        headerPanel.add(textPanel, BorderLayout.WEST);
        headerPanel.add(buttonContainer, BorderLayout.EAST);

        // BAGIAN TABEL - Tetap Sama
        JPanel tableContainer = createProjectTable();

        add(headerPanel, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);
    }

    // ... method createProjectTable() tetep sama ...
    private JPanel createProjectTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));

        String[] columns = {"ID", "Project Name", "Description", "Start Date", "End Date", "Status"};
        
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
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}