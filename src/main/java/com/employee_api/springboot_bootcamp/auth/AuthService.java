package com.employee_api.springboot_bootcamp.auth;

import com.employee_api.springboot_bootcamp.employee.Employee;
import com.employee_api.springboot_bootcamp.employee.EmployeeRepository;
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
    public AuthResponse login(AuthRequest authRequest) {
        Optional<Employee> response = employeeRepository.findByUsername(
                authRequest.username()
        );
        if (response.isPresent() && passwordEncoder.matches(authRequest.password(), response.get().getPassword())) {
            return new AuthResponse(response.get().getId(), authRequest.username(), response.get().getRole());
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
