package com.epam.springLabEpam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.File;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "taskId", updatable = false, nullable = false)
     private int taskId;

     @Column
     private String name;

     @Column
     private boolean isDone;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

     @Column
     @Enumerated(EnumType.STRING)
     private TaskPriority taskPriority;

     @Column
     private String fileName;
}
