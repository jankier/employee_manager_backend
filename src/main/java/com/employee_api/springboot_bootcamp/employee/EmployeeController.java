package com.employee_api.springboot_bootcamp.employee;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTO = employeeService.getAll();
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable UUID id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        employeeService.create(employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@Valid @PathVariable UUID id, @RequestBody Employee employee) {
        EmployeeDTO selectedEmployee = employeeService.getId(id);
        EmployeeDTO updatedEmployee = new EmployeeDTO(
            selectedEmployee.id(),
            employee.getName(),
            employee.getSurname(),
            employee.getEmploymentDate(),
            employee.getSkills(),
            employee.getProjects(),
            employee.getManager()
        );
        employeeService.update(updatedEmployee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable UUID id) {
        EmployeeDTO employeeDTO = employeeService.getId(id);
        employeeService.delete(employeeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
