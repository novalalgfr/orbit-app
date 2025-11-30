package com.orbit.service;

import com.orbit.dao.ProjectDao;
import com.orbit.model.Project;
import java.util.List;

public class ProjectService {

    private ProjectDao projectDao;

    public ProjectService() {
        this.projectDao = new ProjectDao();
    }

    public List<Project> getAllProjects() {
        return projectDao.findAll();
    }

    public Project getProjectById(int id) {
        return projectDao.findById(id);
    }

    public void createProject(String name, String description, String start, String end) {
        Project project = new Project(name, description, start, end);
        projectDao.save(project);
    }

    public void deleteProject(int id) {
        projectDao.delete(id);
    }
}