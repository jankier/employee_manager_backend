package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.constraints.InvalidPassword;
import jakarta.validation.constraints.NotNull;

public record PasswordRequest(String currentPassword,
                              @NotNull(message = "New password cannot be null")
                              @InvalidPassword
                              String newPassword) {
}
