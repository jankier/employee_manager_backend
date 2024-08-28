package com.employee_api.springboot_bootcamp.employee;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeDTO mapToDto(Employee employee);

    Employee mapToEntity(EmployeeDTO employeeDTO);
    
    Employee mapToEntity(PostEmployeeDTO employeeDTO);
}
