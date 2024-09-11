package com.employee_api.springboot_bootcamp.project;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.employee_api.springboot_bootcamp.variables.Authority.ADMIN;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    @PreAuthorize(ADMIN)
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll().stream().map(projectMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    @PreAuthorize(ADMIN)
    public ProjectAllDTO getById(Long id) {
        return projectRepository.findById(id).map(projectMapper::mapToAllDto).orElseThrow(() -> new NoSuchElementException(String.format("Project with the given id %s does not exist", id)));
    }
}
