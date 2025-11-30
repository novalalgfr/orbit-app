package com.orbit.controller;

import com.orbit.model.Project;
import com.orbit.service.ProjectService;
import java.util.List;
import javax.swing.JOptionPane;

public class ProjectController {

    private ProjectService projectService;

    public ProjectController() {
        this.projectService = new ProjectService();
    }

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    public boolean addProject(String name, String desc, String start, String end) {
        if (name.isEmpty() || start.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Project Name and Start Date are required!");
            return false;
        }

        try {
            projectService.createProject(name, desc, start, end);
            JOptionPane.showMessageDialog(null, "Project Created Successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving project: " + e.getMessage());
            return false;
        }
    }
}