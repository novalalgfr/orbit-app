package com.orbit.service;

import com.orbit.dao.TicketDao;
import com.orbit.dao.ProjectDao;
import com.orbit.dao.UserDao;
import com.orbit.model.Project;
import com.orbit.model.Ticket;
import com.orbit.model.User;
import java.util.List;

public class TicketService {

    private TicketDao ticketDao;
    private ProjectDao projectDao;
    private UserDao userDao;

    public TicketService() {
        this.ticketDao = new TicketDao();
        this.projectDao = new ProjectDao();
        this.userDao = new UserDao();
    }

	public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }

    public List<Ticket> getTicketsByProject(int projectId) {
        return ticketDao.findByProjectId(projectId);
    }

    public void createTicket(String title, String priority, String status, int projectId, String assigneeName) {
        Project project = projectDao.findById(projectId);
        User assignee = userDao.findByUsername(assigneeName); 

        if (project != null) {
            Ticket ticket = new Ticket(title, priority, status, project, assignee);
            ticketDao.save(ticket);
        }
    }

    public void updateStatus(Ticket ticket, String newStatus) {
        ticket.setStatus(newStatus);
        ticketDao.update(ticket);
    }
    
    public void deleteTicket(int id) {
        ticketDao.delete(id);
    }
}