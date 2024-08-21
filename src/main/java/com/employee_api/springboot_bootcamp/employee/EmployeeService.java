package com.employee_api.springboot_bootcamp.employee;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll().stream().map(employeeMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO getById(UUID id) {
        return employeeRepository.findById(id).map(employeeMapper::mapToDto).orElseThrow(() ->
                new NoSuchElementException(String.format("Employee with the given id %s does not exist", id)));
    }

    @Transactional
    public void create(EmployeeDTO employeeDTO) {
        employeeRepository.save(employeeMapper.mapToEntity(employeeDTO));
    }

    @Transactional
    public void update(EmployeeDTO selectedEmployee, EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = new EmployeeDTO(
                selectedEmployee.id(),
                employeeDTO.name(),
                employeeDTO.surname(),
                employeeDTO.employmentDate(),
                employeeDTO.skills(),
                employeeDTO.projects(),
                employeeDTO.manager()
        );
        employeeRepository.save(employeeMapper.mapToEntity(updatedEmployee));
    }

    @Transactional
    public void delete(EmployeeDTO employeeDTO) {
        employeeRepository.delete(employeeMapper.mapToEntity(employeeDTO));
    }

}
