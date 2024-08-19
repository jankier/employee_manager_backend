package com.employee_api.springboot_bootcamp.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final ProjectsDTOMapper projectsDTOMapper;

    public List<ProjectsDTO> getAll() {
        return projectsDTOMapper.projectsToDtoProjects(projectsRepository.findAll());
    }
}
