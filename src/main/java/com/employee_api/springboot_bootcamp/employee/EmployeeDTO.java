package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.constraints.NotSameId;
import com.employee_api.springboot_bootcamp.enums.Role;
import com.employee_api.springboot_bootcamp.project.ProjectDTO;
import com.employee_api.springboot_bootcamp.skill.Skill;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@NotSameId
public record EmployeeDTO(
        UUID id,
        @NotEmpty(message = "Name is mandatory")
        @Size(min = 3, max = 25, message = "Invalid name length")
        String name,
        @NotEmpty(message = "Surname is mandatory")
        @Size(min = 3, max = 25, message = "Invalid surname length")
        String surname,
        @NotEmpty(message = "Employment date is mandatory")
        String employmentDate,
        List<Skill> skills,
        List<ProjectDTO> projects,
        ManagerDTO manager,
        Role role) {

}
