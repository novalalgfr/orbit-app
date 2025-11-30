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

    public boolean addTicket(String title, String description, String priority, String status, int projectId, String assigneeName) {
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ticket Title is required!");
            return false;
        }

        try {
            com.orbit.dao.ProjectDao pDao = new com.orbit.dao.ProjectDao();
            com.orbit.dao.UserDao uDao = new com.orbit.dao.UserDao();
            com.orbit.dao.TicketDao tDao = new com.orbit.dao.TicketDao();
            
            com.orbit.model.Project project = pDao.findById(projectId);
            com.orbit.model.User assignee = uDao.findByUsername(assigneeName);
            
            if (project != null) {
                Ticket t = new Ticket(title, description, priority, status, project, assignee);
                
                tDao.save(t);
                JOptionPane.showMessageDialog(null, "Ticket Created Successfully!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating ticket.");
        }
        return false;
    }
    
    public boolean updateTicket(int id, String title, String description, String priority, String status, String assigneeName) {
        try {
            com.orbit.dao.TicketDao dao = new com.orbit.dao.TicketDao();
            Ticket ticket = dao.findAll().stream().filter(t -> t.getId() == id).findFirst().orElse(null);
            
            if (ticket != null) {
                ticket.setTitle(title);
                ticket.setDescription(description);
                ticket.setPriority(priority);
                ticket.setStatus(status);
                
                if (assigneeName != null) {
                    com.orbit.model.User user = new com.orbit.dao.UserDao().findByUsername(assigneeName);
                    ticket.setAssignee(user);
                }
                
                dao.update(ticket);
                JOptionPane.showMessageDialog(null, "Ticket Updated Successfully!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}