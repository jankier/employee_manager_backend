package com.employee_api.springboot_bootcamp.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDTOMapper employeeDTOMapper;

    public List<EmployeeDTO> getAll() {
        return employeeDTOMapper.employeesToDtoEmployees(employeeRepository.findAll());
    }

    public EmployeeDTO getById(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("Employee with the given id %s does not exist", id));
        }
        return employeeDTOMapper.employeeToDtoEmployee(employeeRepository.findById(id).orElse(null));
    }

    public void create(EmployeeDTO employeeDTO) {
        employeeRepository.save(employeeDTOMapper.dtoEmployeeToEmployee(employeeDTO));
    }

    public EmployeeDTO getId(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException(String.format("Employee with the given id %s does not exist", id));
        }
        return employeeDTOMapper.employeeToDtoEmployee(employeeRepository.findById(id).orElse(null));
    }

    public void update(EmployeeDTO employeeDTO) {
        employeeRepository.save(employeeDTOMapper.dtoEmployeeToEmployee(employeeDTO));
    }

    public void delete(EmployeeDTO employeeDTO) {
        employeeRepository.delete(employeeDTOMapper.dtoEmployeeToEmployee(employeeDTO));
    }

}
