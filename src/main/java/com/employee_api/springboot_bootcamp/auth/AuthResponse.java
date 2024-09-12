package com.employee_api.springboot_bootcamp.auth;

import com.employee_api.springboot_bootcamp.enums.Role;

import java.util.UUID;

public record AuthResponse(UUID id, String username, Role role) {
}
