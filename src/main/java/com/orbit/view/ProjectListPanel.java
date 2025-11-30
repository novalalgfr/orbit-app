package com.orbit.view;

import com.orbit.controller.ProjectController;
import com.orbit.model.Project;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ProjectListPanel extends JPanel {

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

    private void loadData() {
        // Kosongkan tabel dulu
        tableModel.setRowCount(0);

        // Ambil data dari database via Controller
        List<Project> projects = controller.getAllProjects();

        // Ambil tanggal hari ini
        LocalDate today = LocalDate.now();

        // Masukkan ke tabel
        for (Project p : projects) {
            String status = "Active"; // Default status

            // Logika Cek Tanggal
            try {
                // Konversi String tanggal dari database ke LocalDate
                if (p.getEndDate() != null && !p.getEndDate().isEmpty()) {
                    LocalDate endDate = LocalDate.parse(p.getEndDate()); // Format harus YYYY-MM-DD
                    
                    if (endDate.isBefore(today)) {
                        status = "Completed";
                    }
                }
            } catch (DateTimeParseException e) {
                // Jika format tanggal salah, biarkan default "Active"
                System.err.println("Error parsing date for project: " + p.getProjectName());
            }

            Object[] row = {
                p.getId(),
                p.getProjectName(),
                p.getDescription(),
                p.getStartDate(),
                p.getEndDate(),
                status // Status sekarang dinamis berdasarkan tanggal!
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
        JLabel title = new JLabel("All Projects");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 33, 33));
        
        JLabel subtitle = new JLabel("Manage your projects");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        
        textPanel.add(title);
        textPanel.add(subtitle);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonContainer.setOpaque(false);

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
                int projectId = (int) table.getValueAt(selectedRow, 0);
                String projectName = (String) table.getValueAt(selectedRow, 1);
                
                Container parent = SwingUtilities.getAncestorOfClass(JPanel.class, this);
                if (parent != null) {
                    CardLayout cl = (CardLayout) parent.getLayout();
                    cl.show(parent, "TICKETS");
                    
                    System.out.println("Opening Project ID: " + projectId);
                }
            }
        });

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
            dialog.setVisible(true); // Program berhenti disini sampai dialog ditutup
            
            // Setelah dialog ditutup, refresh tabel!
            loadData();
        });

        buttonContainer.add(btnOpen);
        buttonContainer.add(btnAdd);

        headerPanel.add(textPanel, BorderLayout.WEST);
        headerPanel.add(buttonContainer, BorderLayout.EAST);

        // BAGIAN TABEL
        JPanel tableContainer = createProjectTable();

        add(headerPanel, BorderLayout.NORTH);
        add(tableContainer, BorderLayout.CENTER);
    }

    private JPanel createProjectTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));

        String[] columns = {"ID", "Project Name", "Description", "Start Date", "End Date", "Status"};
        
        // Inisialisasi Model Kosong (Data diisi via loadData)
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
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