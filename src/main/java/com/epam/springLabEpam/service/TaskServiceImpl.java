package com.epam.springLabEpam.service;

import com.epam.springLabEpam.dao.TasksDao;
import com.epam.springLabEpam.dao.UsersDao;
import com.epam.springLabEpam.dto.TaskDto;
import com.epam.springLabEpam.dto.UserDto;
import com.epam.springLabEpam.exception.NoSuchTaskException;
import com.epam.springLabEpam.model.Task;
import com.epam.springLabEpam.model.TaskPriority;
import com.epam.springLabEpam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private TasksDao tasksDao;

    private String UPLOAD_PATH = "classpath:\\resources\\taskFiles";
    @Autowired
    private UsersDao usersDao;

    @Autowired
    TaskServiceImpl(TasksDao tasksDao){
        this.tasksDao = tasksDao;
    }

    public void createTask(UserDto user, TaskDto task) {
        User userFromDB = usersDao.findByEmail(user.getEmail());
        Task newTask = Task.builder()
                .name(task.getName())
                .isDone(task.isDone())
                .taskPriority(task.getTaskPriority())
                .fileName(task.getFileName())
                .user(userFromDB)
                .build();
        tasksDao.save(newTask);
    }

    @Override
    public void deleteTask(TaskDto task) {
        tasksDao.deleteById(task.getId());
    }

    @Override
    public List<TaskDto> getAllTask() {
        List<Task> taskList = tasksDao.findAll(Sort.by(Sort.Order.asc("taskPriority")));
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (Task task: taskList) {
            TaskDto taskDto = TaskDto.builder()
                    .id(task.getTaskId())
                    .name(task.getName())
                    .isDone(task.isDone())
                    .userId(task.getUser().getId())
                    .taskPriority(task.getTaskPriority())
                    .fileName(task.getFileName())
                    .build();
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    @Override
    public void setDone(TaskDto task) {
        task.setDone(true);
        Task taskFromDB = tasksDao.getOne(task.getId());
        taskFromDB.setDone(true);
        tasksDao.update(taskFromDB.getTaskId(), taskFromDB.isDone());
    }

    @Override
    public void setUndone(TaskDto task) {
        task.setDone(false);
        Task taskFromDB = tasksDao.getOne(task.getId());
        taskFromDB.setDone(false);
        tasksDao.update(taskFromDB.getTaskId(), taskFromDB.isDone());
    }

    public void setPriority(TaskDto task, TaskPriority taskPriority) {
        Task taskFromDB = tasksDao.getOne(task.getId());
        task.setTaskPriority(taskPriority);
        taskFromDB.setTaskPriority(taskPriority);
        tasksDao.updatePriority(taskFromDB.getTaskId(), taskFromDB.getTaskPriority());
    }

    public void attachFile(TaskDto task, MultipartFile file) {
        Task taskFromDB = tasksDao.findById(task.getId()).orElseThrow(() -> new NoSuchTaskException());
        File dest = new File(UPLOAD_PATH);
        if (!dest.exists()) {
            dest.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        try {
            file.transferTo(new File(UPLOAD_PATH + "/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        taskFromDB.setFileName(fileName);
        tasksDao.updateFileName(taskFromDB.getTaskId(), fileName);
    }

    @Override
    public List<TaskDto> getUserTasks(int id) {
        List<Task> taskList = tasksDao.findAllByUserId(id);
        List<TaskDto> taskDtoList = new ArrayList<>();
        for (Task task: taskList) {
            TaskDto taskDto = TaskDto.builder()
                    .id(task.getTaskId())
                    .name(task.getName())
                    .isDone(task.isDone())
                    .userId(task.getUser().getId())
                    .taskPriority(task.getTaskPriority())
                    .fileName(task.getFileName())
                    .build();
            taskDtoList.add(taskDto);
        }
        return taskDtoList;

    }
}
