package com.employee_api.springboot_bootcamp.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTO = employeeService.getAll();
        if (employeeDTO.isEmpty()) {
            log.info("No employee found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Fetched all employees");
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable UUID id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        log.info("Fetched employee with id {}", id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.create(employeeDTO);
        log.info("Employee created successfully with {}", employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO selectedEmployee = employeeService.getById(employeeDTO.id());
        employeeService.update(selectedEmployee, employeeDTO);
        log.info("Employee updated successfully with {}", employeeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable UUID id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        employeeService.delete(employeeDTO);
        log.info("Employee deleted successfully with {}", employeeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}