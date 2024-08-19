package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.projects.ProjectsDTOMapper;
import com.employee_api.springboot_bootcamp.skills.SkillsDTOMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeDTOMapper  {
    EmployeeDTO employeeToDtoEmployee(Employee employee);

    List<EmployeeDTO> employeesToDtoEmployees(List<Employee> employees);

    Employee dtoEmployeeToEmployee(EmployeeDTO employeeDTO);

    List<Employee> dtoEmployeesToEmployees(List<EmployeeDTO> employeeDTOList);
}
