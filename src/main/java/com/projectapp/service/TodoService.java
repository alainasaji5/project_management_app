package com.projectapp.service;

import com.projectapp.model.Project;
import com.projectapp.model.Todo;
import com.projectapp.model.TodoStatus;
import com.projectapp.repository.ProjectRepository;
import com.projectapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public void addTodoToProject(Long projectId, String description, String status,
                                 LocalDateTime createdDate, LocalDateTime updatedDate) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + projectId));
        Todo todo = new Todo();
        todo.setDescription(description);
        todo.setStatus(TodoStatus.valueOf(status));
        todo.setCreatedDate(createdDate);
        todo.setUpdatedDate(updatedDate);
        todo.setProject(project);
        todoRepository.save(todo);
    }

    public void updateTodo(Long todoId, String description, String status, LocalDateTime updatedDate) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + todoId));
        todo.setDescription(description);
        todo.setStatus(TodoStatus.valueOf(status));
        todo.setUpdatedDate(updatedDate);
        todoRepository.save(todo);
    }

    public void deleteTodoById(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
