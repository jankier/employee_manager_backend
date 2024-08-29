package com.employee_api.springboot_bootcamp.auth;

import com.employee_api.springboot_bootcamp.employee.Employee;
import com.employee_api.springboot_bootcamp.employee.EmployeeRepository;
import com.employee_api.springboot_bootcamp.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void login(AuthRequest authRequest) {
        Optional<Employee> response = employeeRepository.findByUsername(
                authRequest.username()
        );
        if (response.isPresent() && response.get().getRole() == Role.ADMIN && passwordEncoder.matches(authRequest.password(), response.get().getPassword())) {
            return;
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}