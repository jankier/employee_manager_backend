package com.employee_api.springboot_bootcamp.projects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name="projects")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;
}
