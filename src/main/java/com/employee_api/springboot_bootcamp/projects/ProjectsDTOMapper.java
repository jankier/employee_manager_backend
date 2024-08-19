package com.employee_api.springboot_bootcamp.projects;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectsDTOMapper {
    List<ProjectsDTO> projectsToDtoProjects(List<Projects> projects);
}
