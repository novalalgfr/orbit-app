package com.orbit.controller;

import com.orbit.service.AuthService;
import com.orbit.service.ProjectService;
import com.orbit.service.TicketService;
import com.orbit.model.Project;
import com.orbit.model.Ticket;
import com.orbit.model.User;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {

    private ProjectService projectService;
    private TicketService ticketService;

    public DashboardController() {
        this.projectService = new ProjectService();
        this.ticketService = new TicketService();
    }

    public String getGreetingName() {
        User user = AuthService.getCurrentUser();
        return (user != null) ? user.getFullName() : "Guest";
    }

    public int getTotalProjects() {
        List<Project> list = projectService.getAllProjects();
        return (list != null) ? list.size() : 0;
    }

    public int getPendingTasksCount() {
        User currentUser = AuthService.getCurrentUser();
        List<Ticket> allTickets = ticketService.getAllTickets();
        if (allTickets == null || currentUser == null) return 0;

        if ("ADMIN".equals(currentUser.getRole())) {
            return (int) allTickets.stream()
                    .filter(t -> t.getStatus().equals("TODO"))
                    .count();
        }

        return (int) allTickets.stream()
                .filter(t -> t.getAssignee() != null && t.getAssignee().getId() == currentUser.getId())
                .filter(t -> t.getStatus().equals("TODO"))
                .count();
    }

	public int getMyTasksCount() {
        User currentUser = AuthService.getCurrentUser();
        List<Ticket> allTickets = ticketService.getAllTickets();
        if (allTickets == null || currentUser == null) return 0;

        if ("ADMIN".equals(currentUser.getRole())) {
            return (int) allTickets.stream()
                    .filter(t -> t.getStatus().equals("IN_PROGRESS"))
                    .count();
        } 
        
        return (int) allTickets.stream()
                .filter(t -> t.getAssignee() != null && t.getAssignee().getId() == currentUser.getId())
                .filter(t -> t.getStatus().equals("IN_PROGRESS"))
                .count();
    }

    public int getCompletedTasksCount() {
        User currentUser = AuthService.getCurrentUser();
        List<Ticket> allTickets = ticketService.getAllTickets();
        if (allTickets == null || currentUser == null) return 0;

        if ("ADMIN".equals(currentUser.getRole())) {
            return (int) allTickets.stream()
                    .filter(t -> t.getStatus().equals("DONE"))
                    .count();
        }

        return (int) allTickets.stream()
                .filter(t -> t.getAssignee() != null && t.getAssignee().getId() == currentUser.getId())
                .filter(t -> t.getStatus().equals("DONE"))
                .count();
    }

    public List<Ticket> getRecentActivities() {
        User currentUser = AuthService.getCurrentUser();
        
        List<Ticket> all = ticketService.getAllTickets();
        if (all == null || all.isEmpty() || currentUser == null) return List.of();
        
        if ("ADMIN".equals(currentUser.getRole())) {
            return all.stream()
                    .sorted((t1, t2) -> Integer.compare(t2.getId(), t1.getId()))
                    .limit(15)
                    .collect(Collectors.toList());
        } else {
            return all.stream()
                    .filter(t -> t.getAssignee() != null && t.getAssignee().getId() == currentUser.getId())
                    // .filter(t -> !t.getStatus().equals("DONE")) 
                    .sorted((t1, t2) -> Integer.compare(t2.getId(), t1.getId()))
                    .limit(15)
                    .collect(Collectors.toList());
        }
    }
}