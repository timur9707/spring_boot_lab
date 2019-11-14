package com.epam.springLabEpam.dao;


import com.epam.springLabEpam.dto.TaskDto;
import com.epam.springLabEpam.model.Task;
import com.epam.springLabEpam.model.TaskPriority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TasksDao extends JpaRepository<Task, Integer> {
    @Override
    List<Task> findAll(Sort sort);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Task t set t.isDone = :isDone where t.taskId = :taskId")
    void update(@Param("taskId") int taskId, @Param("isDone") Boolean isDone);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Task t set t.taskPriority = :taskPriority where t.taskId = :taskId")
    void updatePriority(@Param("taskId")int taskId, @Param("taskPriority")TaskPriority taskPriority);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Task t set t.fileName = :fileName where t.taskId = :taskId")
    void updateFileName(@Param("taskId")int taskId, @Param("fileName")String fileName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "select * from Task  where user_id = :user_id")
    List<Task> findAllByUserId(@Param("user_id")int userId);


}
