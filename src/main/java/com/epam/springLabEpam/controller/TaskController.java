package com.epam.springLabEpam.controller;

import com.epam.springLabEpam.dto.TaskDto;
import com.epam.springLabEpam.dto.UserDto;
import com.epam.springLabEpam.model.Task;
import com.epam.springLabEpam.model.TaskPriority;
import com.epam.springLabEpam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/createTask")
    public void createTask(@RequestBody UserDto user, TaskDto task) {
        taskService.createTask(user, task);
    }

    @DeleteMapping("/{taskId}/deleteTask")
    public void deleteTask(TaskDto task) {
        taskService.deleteTask(task);
    }

    @GetMapping("/getAllTasks")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTask();
    }

    @PutMapping("/{taskId}/setDone")
    public void setDone(TaskDto task) {
        taskService.setDone(task);
    }

    @GetMapping("/getUserTasks")
    public List<TaskDto> getUserTasks(int userId) {
        return  taskService.getUserTasks(userId);
    }

    @PutMapping("/{taskId}/setUndone")
    public void setUndone(TaskDto task) {
        taskService.setUndone(task);
    }

    @PutMapping("/setPriority/{taskPriority}")
    public void setPriority(TaskDto task, TaskPriority taskPriority) {
        taskService.setPriority(task, taskPriority);
    }

    @PutMapping("/attachFile/{taskId}")
    public void attachFile(TaskDto task, MultipartFile file) {
        taskService.attachFile(task, file);
    }

}
