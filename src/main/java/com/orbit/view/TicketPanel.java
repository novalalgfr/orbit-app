package com.orbit.view;

import com.orbit.controller.TicketController;
import com.orbit.controller.ProjectController;
import com.orbit.model.Ticket;
import com.orbit.model.Project;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TicketPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblProjectTitle;
    private JLabel lblSubtitle;
    
    // Controller
    private TicketController ticketController;
    private ProjectController projectController;
    
    // Data Project yang sedang dibuka
    private int currentProjectId = -1;
    private String currentProjectName = "";

    // Callback untuk kembali ke halaman project
    private Runnable onBackAction;

    public TicketPanel(Runnable onBackAction) {
        this.onBackAction = onBackAction;
        this.ticketController = new TicketController();
        this.projectController = new ProjectController();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); 
        setBorder(new EmptyBorder(30, 30, 30, 30)); 

        initComponents();
    }

    // Method ini akan dipanggil dari ProjectListPanel / MainFrame
    public void loadData(int projectId) {
        this.currentProjectId = projectId;
        
        // 1. Ambil Info Project (untuk Header)
        // Kita perlu bikin method getProjectById di ProjectController dulu (nanti dicek)
        // Sementara kita pakai logic simple atau asumsikan nama sudah dikirim
        // Tapi idealnya ambil fresh dari DB:
        try {
            // Asumsi: Kita tambahkan method getProjectById di Controller nanti
            // Kalau belum ada, ProjectController perlu update sedikit.
            // Untuk sekarang kita ambil tiketnya dulu.
            List<Ticket> tickets = ticketController.getTicketsByProject(projectId);
            
            // Update Header (Placeholder logic, nanti diperbaiki jika ProjectController sudah ada getById)
            lblProjectTitle.setText("Project ID #" + projectId); 
            lblSubtitle.setText("Total Tickets: " + tickets.size());
            
            // 2. Update Tabel
            tableModel.setRowCount(0);
            for (Ticket t : tickets) {
                Object[] row = {
                    t.getId(),
                    t.getTitle(),
                    (t.getAssignee() != null) ? t.getAssignee().getFullName() : "Unassigned",
                    t.getPriority(),
                    t.getStatus()
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Versi lebih lengkap jika ProjectController sudah support getById
    public void setProjectHeader(String name) {
        this.currentProjectName = name;
        lblProjectTitle.setText(name);
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
        textPanel.setBorder(new EmptyBorder(0, 15, 0, 0)); 

        lblProjectTitle = new JLabel("Loading Project..."); 
        lblProjectTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblProjectTitle.setForeground(new Color(33, 33, 33));
        
        lblSubtitle = new JLabel("Tickets Overview");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(Color.GRAY);
        
        textPanel.add(lblProjectTitle);
        textPanel.add(lblSubtitle);

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
            if (currentProjectId == -1) {
                JOptionPane.showMessageDialog(this, "No project selected!");
                return;
            }
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            // Kita kirim ID Project ke Form Dialog
            TicketFormDialog dialog = new TicketFormDialog(parentFrame, currentProjectId);
            dialog.setVisible(true);
            
            // Refresh tabel setelah dialog tutup
            loadData(currentProjectId);
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

        // Atur Lebar Kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(60); 
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(300); 
        table.getColumnModel().getColumn(2).setPreferredWidth(150); 
        table.getColumnModel().getColumn(3).setPreferredWidth(100); 
        table.getColumnModel().getColumn(4).setPreferredWidth(100); 

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}