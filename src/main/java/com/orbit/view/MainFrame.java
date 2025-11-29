package com.orbit.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private final Color COLOR_SIDEBAR = new Color(30, 30, 46); 
    private final Color COLOR_BG = new Color(245, 247, 250);   
    private final Color COLOR_ACCENT = new Color(54, 153, 255); 
    private final Color COLOR_TEXT_MENU = new Color(162, 163, 183); 

    public MainFrame() {
        initWindow();
        initLayout();
        showView("DASHBOARD"); 
    }

    private void initWindow() {
        setTitle("Orbit - Project Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setSize(1280, 720);
        setLocationRelativeTo(null);
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        initSidebar();

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(COLOR_BG);

        contentPanel.add(new DashboardPanel(), "DASHBOARD");
        
        contentPanel.add(new ProjectListPanel(), "PROJECTS");
        
        contentPanel.add(new UserListPanel(), "USERS");

		contentPanel.add(new TicketPanel(() -> showView("PROJECTS")), "TICKETS");

        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(COLOR_SIDEBAR);
        sidebarPanel.setPreferredSize(new Dimension(260, getHeight()));
        sidebarPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblLogo = new JLabel("ORBIT");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Project Management");
        lblSubtitle.setForeground(COLOR_TEXT_MENU);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnDash = createMenuButton("Dashboard", e -> showView("DASHBOARD"));
        JButton btnProj = createMenuButton("All Projects", e -> showView("PROJECTS"));
        JButton btnUser = createMenuButton("Team Members", e -> showView("USERS"));
        
        JButton btnLogout = createMenuButton("Logout", e -> {
            JOptionPane.showMessageDialog(this, "Logout clicked");
        });
        btnLogout.setForeground(new Color(255, 82, 82)); 

        sidebarPanel.add(lblLogo);
        sidebarPanel.add(lblSubtitle);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 50))); 
        
        sidebarPanel.add(createSectionTitle("MAIN MENU"));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnDash);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarPanel.add(btnProj);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebarPanel.add(btnUser);
        
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(createSectionTitle("SYSTEM"));
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnLogout);
    }

    private void showView(String cardName) {
        cardLayout.show(contentPanel, cardName);
    }

    private JLabel createSectionTitle(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(90, 90, 110));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(COLOR_TEXT_MENU);
        btn.setBackground(COLOR_SIDEBAR); 
        btn.setBorder(new EmptyBorder(12, 15, 12, 0));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(40, 40, 60)); 
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 0, COLOR_ACCENT),
                    new EmptyBorder(12, 11, 12, 0)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_SIDEBAR);
                btn.setForeground(COLOR_TEXT_MENU);
                btn.setBorder(new EmptyBorder(12, 15, 12, 0)); 
            }
        });

        btn.addActionListener(action);
        return btn;
    }
}