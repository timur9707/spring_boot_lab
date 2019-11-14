package com.epam.springLabEpam.dto;

import com.epam.springLabEpam.model.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class  TaskDto {
    private int id;
    @NotNull
    private String name;
    private boolean isDone;
    private int userId;
    private TaskPriority taskPriority;
    private String fileName;
}
