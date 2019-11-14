package com.epam.springLabEpam.service;

import com.epam.springLabEpam.dto.TaskDto;
import com.epam.springLabEpam.dto.UserDto;
import com.epam.springLabEpam.model.TaskPriority;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    void createTask(UserDto user, TaskDto task);

    void deleteTask(TaskDto task);

    List<TaskDto> getAllTask();

    void setDone(TaskDto task);

    void setUndone(TaskDto task);

    void setPriority(TaskDto task, TaskPriority taskPriority);

    void attachFile(TaskDto task, MultipartFile file);

    List<TaskDto> getUserTasks(int id);
}
