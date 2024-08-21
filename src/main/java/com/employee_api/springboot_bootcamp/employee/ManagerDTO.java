package com.employee_api.springboot_bootcamp.employee;

import java.util.UUID;

public record ManagerDTO(UUID id, String name, String surname) {

}
