package com.projectapp.controller;

import com.projectapp.model.Project;
import com.projectapp.service.ProjectService;
import com.projectapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TodoService todoService;

    @GetMapping("/projects")
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAllProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/projects/view")
    public String viewProjectByName(@RequestParam String name, Model model) {
        Project project = projectService.findProjectByName(name);
        if (project != null) {
            model.addAttribute("project", project);
            return "project_details";
        } else {

            model.addAttribute("error", "Project not found");
            return "error";
        }
    }
     @GetMapping("/projects/new")
    public String createProjectForm() {
        return "create_project";
    }

    @PostMapping("/projects/{projectId}/todos")
    public String addTodo(@PathVariable Long projectId,
                          @RequestParam String description,
                          @RequestParam String status,
                          @RequestParam String createdDate,
                          @RequestParam String updatedDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime createdDateTime = LocalDateTime.parse(createdDate, formatter);
        LocalDateTime updatedDateTime = LocalDateTime.parse(updatedDate, formatter);

        todoService.addTodoToProject(projectId, description, status, createdDateTime, updatedDateTime);

        return "redirect:/projects/view?name=" + projectService.findProjectById(projectId).getName();
    }

    @PostMapping("/projects/{projectId}/todos/{todoId}/update")
    public String updateTodo(@PathVariable Long projectId,
                             @PathVariable Long todoId,
                             @RequestParam String description,
                             @RequestParam String status,
                             @RequestParam String updatedDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime updatedDateTime = LocalDateTime.parse(updatedDate, formatter);

        todoService.updateTodo(todoId, description, status, updatedDateTime);

        return "redirect:/projects/view?name=" + projectService.findProjectById(projectId).getName();
    }

    @PostMapping("/projects/{projectId}/todos/{todoId}/delete")
    public String deleteTodo(@PathVariable Long projectId,
                             @PathVariable Long todoId) {

        todoService.deleteTodoById(todoId);

        return "redirect:/projects/view?name=" + projectService.findProjectById(projectId).getName();
    }
}
