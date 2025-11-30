package com.orbit.controller;

import com.orbit.model.Ticket;
import com.orbit.service.TicketService;
import java.util.List;
import javax.swing.JOptionPane;

public class TicketController {

    private TicketService ticketService;

    public TicketController() {
        this.ticketService = new TicketService();
    }

    public List<Ticket> getTicketsByProject(int projectId) {
        return ticketService.getTicketsByProject(projectId);
    }

    public boolean addTicket(String title, String priority, String status, int projectId, String assigneeName) {
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ticket Title is required!");
            return false;
        }

        try {
            ticketService.createTicket(title, priority, status, projectId, assigneeName);
            JOptionPane.showMessageDialog(null, "Ticket Created Successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating ticket.");
            return false;
        }
    }
    
    public void updateStatus(Ticket ticket, String newStatus) {
        ticketService.updateStatus(ticket, newStatus);
    }
}