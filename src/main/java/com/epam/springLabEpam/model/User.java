package com.epam.springLabEpam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column
    @NotEmpty
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    @NotEmpty
    private String email;

    @Column
    @NotEmpty
    private String password;

    @Column
    private String subscription;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

   @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE})
    private List<Task> taskList;
}
