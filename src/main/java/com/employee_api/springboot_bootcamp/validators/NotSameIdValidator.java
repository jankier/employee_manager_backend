package com.employee_api.springboot_bootcamp.validators;

import com.employee_api.springboot_bootcamp.constraints.NotSameId;
import com.employee_api.springboot_bootcamp.employee.EmployeeDTO;
import com.employee_api.springboot_bootcamp.employee.ManagerDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotSameIdValidator implements ConstraintValidator<NotSameId, EmployeeDTO> {
    @Override
    public void initialize(NotSameId constraintAnnotation) {
    }

    @Override
    public boolean isValid(EmployeeDTO employee, ConstraintValidatorContext constraintValidatorContext) {
        ManagerDTO manager = employee.manager();
        if (manager == null) {
            return true;
        }
        return !employee.id().equals(manager.id());
    }
}
