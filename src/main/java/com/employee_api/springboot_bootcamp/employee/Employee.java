package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.project.Project;
import com.employee_api.springboot_bootcamp.skill.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, max = 25, message = "Invalid name length")
    private String name;

    @NotEmpty(message = "Surname is mandatory")
    @Size(min = 3, max = 25, message = "Invalid surname length")
    private String surname;

    @NotEmpty(message = "Employment date is mandatory")
    private String employmentDate;

    @ManyToMany
    private List<Skill> skills;

    @ManyToMany
    private List<Project> projects;

    @ManyToOne
    private Employee manager;
}
