package com.employee_api.springboot_bootcamp.project;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    ProjectDTO mapToDto(Project project);

    ProjectAllDTO mapToAllDto(Project project);
}
