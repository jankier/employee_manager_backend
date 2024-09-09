package com.employee_api.springboot_bootcamp.employee;

public record PasswordRequest(String currentPassword, String newPassword) {
}
