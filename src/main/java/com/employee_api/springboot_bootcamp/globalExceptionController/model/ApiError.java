package com.employee_api.springboot_bootcamp.globalExceptionController.model;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        int code,
        String message,
        List<String> errors,
        String path,
        LocalDateTime timestamp
) {
}
