package com.projectapp.service;

import com.projectapp.model.Project;
import com.projectapp.model.Todo;
import com.projectapp.model.TodoStatus;
import com.projectapp.repository.ProjectRepository;
import com.projectapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TodoRepository todoRepository;

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Project findProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project findProjectByName(String name) {
        return projectRepository.findByName(name);
    }

    public void createProjectWithTodo(String name, String description,
                                      String todoDescription, String todoStatus,
                                      String todoCreatedDate, String todoUpdatedDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime createdDate = LocalDateTime.parse(todoCreatedDate, formatter);
        LocalDateTime updatedDate = LocalDateTime.parse(todoUpdatedDate, formatter);

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setCreatedDate(LocalDateTime.now());
        project.setUpdatedDate(LocalDateTime.now());

        project = projectRepository.save(project);

        Todo todo = new Todo();
        todo.setDescription(todoDescription);
        todo.setStatus(TodoStatus.valueOf(todoStatus));
        todo.setCreatedDate(createdDate);
        todo.setUpdatedDate(updatedDate);
        todo.setProject(project);

        todoRepository.save(todo);
    }
}
