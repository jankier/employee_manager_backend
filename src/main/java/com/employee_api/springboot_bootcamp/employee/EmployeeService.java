package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(employeeMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO getById(UUID id) {
        return employeeRepository.findById(id).map(employeeMapper::mapToDto).orElseThrow(() ->
                new NoSuchElementException(String.format("Employee with the given id %s does not exist", id)));
    }

    @Transactional
    public List<EmployeeDTO> getManagers(UUID id) {
        List<EmployeeDTO> employees = this.getAll();
        List<EmployeeDTO> subordinates = employees.stream().flatMap(employee -> {
            if (employee.id().equals(id)) {
                return Stream.of(employee);
            }
            if (Objects.nonNull(employee.manager()) && employee.manager().id().equals(id)) {
                List<EmployeeDTO> subordinatesList = this.findSubordinates(employee, employees);
                subordinatesList.add(employee);
                return subordinatesList.stream();
            }
            return Stream.empty();
        }).toList();
        return employees.stream().filter(employee -> !subordinates.contains(employee)).toList();
    }

    @Transactional
    public void create(PostEmployeeDTO employeeDTO) {
        Role currentRole;
        if (employeeDTO.manager() == null) {
            currentRole = Role.ADMIN;
        } else {
            currentRole = Role.USER;
        }
        Employee employee = employeeMapper.mapToEntity(employeeDTO);
        employee.setRole(currentRole);
        employee.setUsername((employeeDTO.name() + employeeDTO.surname()).toLowerCase());
        employee.setPassword(passwordEncoder.encode(employeeDTO.surname().toLowerCase()));
        employeeRepository.save(employee);
    }

    @Transactional
    public void update(EmployeeDTO selectedEmployee, EmployeeDTO employeeDTO) {
        Role currentRole;
        if (employeeDTO.manager() == null) {
            currentRole = Role.ADMIN;
        } else {
            currentRole = Role.USER;
        }
        EmployeeDTO updatedEmployee = new EmployeeDTO(
                selectedEmployee.id(),
                employeeDTO.name(),
                employeeDTO.surname(),
                employeeDTO.employmentDate(),
                employeeDTO.skills(),
                employeeDTO.projects(),
                employeeDTO.manager(),
                currentRole
        );
        Employee employee = employeeMapper.mapToEntity(updatedEmployee);
        employee.setUsername((employeeDTO.name() + employeeDTO.surname()).toLowerCase());
        employee.setPassword(passwordEncoder.encode(employeeDTO.surname().toLowerCase()));
        employeeRepository.save(employee);
    }

    @Transactional
    public void delete(EmployeeDTO employeeDTO) {
        List<EmployeeDTO> employees = this.getAll();
        List<EmployeeDTO> subordinates = employees.stream()
                .filter(employee -> Objects.nonNull(employee.manager()) && employee.manager().id().equals(employeeDTO.id())).toList();
        if (!subordinates.isEmpty()) {
            throw new IllegalArgumentException(String.format("Cannot delete %s %s because of manager position", employeeDTO.name(), employeeDTO.surname()));
        }
        employeeRepository.delete(employeeMapper.mapToEntity(employeeDTO));
    }

    private List<EmployeeDTO> findSubordinates(EmployeeDTO employeeDTO, List<EmployeeDTO> allEmployees) {
        return allEmployees.stream().flatMap(employee -> {
            if (Objects.nonNull(employee.manager()) && employee.manager().id().equals(employeeDTO.id())) {
                List<EmployeeDTO> subordinatesList = this.findSubordinates(employee, allEmployees);
                subordinatesList.add(employee);
                return subordinatesList.stream();
            }
            return Stream.empty();
        }).collect(Collectors.toList());
    }
}
