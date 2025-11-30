package com.orbit.controller;

import com.orbit.service.ProjectService;
import com.orbit.model.Project;
import java.util.List;

public class DashboardController {

    private ProjectService projectService;

    public DashboardController() {
        this.projectService = new ProjectService();
    }

    public int getTotalProjects() {
        List<Project> list = projectService.getAllProjects();
        return (list != null) ? list.size() : 0;
    }
    
}