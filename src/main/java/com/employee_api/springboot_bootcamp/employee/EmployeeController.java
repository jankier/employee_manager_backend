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
            log.info("No employees/managers found");
        }
        log.info("Fetched all employees/managers");
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable UUID id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        log.info("Fetched employee with id {}", id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("managers/{id}")
    public ResponseEntity<List<EmployeeDTO>> getAllManagers(@PathVariable UUID id) {
        List<EmployeeDTO> employeeDTO = employeeService.getManagers(id);
        log.info("Fetched all managers for manager with id {}", id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addEmployee(@Valid @RequestBody PostEmployeeDTO employeeDTO) {
        employeeService.create(employeeDTO);
        log.info("Employee created successfully with {}", employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateEmployee(@PathVariable UUID id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(id, employeeDTO);
        log.info("Employee updated successfully with {}", employeeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<HttpStatus> updatePassword(@PathVariable UUID id, @Valid @RequestBody PasswordRequest passwordRequest) {
        employeeService.updatePassword(id, passwordRequest);
        log.info("Password updated successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable UUID id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        employeeService.delete(employeeDTO);
        log.info("Employee deleted successfully with {}", employeeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
