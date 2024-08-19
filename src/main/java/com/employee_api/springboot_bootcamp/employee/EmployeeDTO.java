package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.projects.Projects;
import com.employee_api.springboot_bootcamp.skills.Skills;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record EmployeeDTO (UUID id,
                           @NotEmpty(message = "Name is mandatory")
                           @Size(min = 3, max = 25, message = "Invalid name length")
                           String name,
                           @NotEmpty(message = "Surname is mandatory")
                           @Size(min = 3, max = 25, message = "Invalid surname length")
                           String surname,
                           @NotEmpty(message = "Employment date is mandatory")
                           String employmentDate,
                           List<Skills> skills,
                           List<Projects> projects,
                           String manager) {

}
