package com.employee_api.springboot_bootcamp.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projectsDTO = projectService.getAll();
        if (projectsDTO.isEmpty()) {
            log.info("No projects found");
        }
        log.info("Fetched all projects");
        return new ResponseEntity<>(projectsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAllDTO> getProject(@PathVariable Long id) {
        ProjectAllDTO projectAllDTO = projectService.getById(id);
        log.info("Fetched project with id {}", id);
        return new ResponseEntity<>(projectAllDTO, HttpStatus.OK);
    }
}
