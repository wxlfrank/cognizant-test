package org.test.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.api.model.Task;
import org.test.api.repositories.TaskRepository;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository repository;

    @GetMapping
    @CrossOrigin("*")
    public List<Task> get() {
        return this.repository.findAll();
    }
}
