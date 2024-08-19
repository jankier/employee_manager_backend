package com.employee_api.springboot_bootcamp.projects;

import com.employee_api.springboot_bootcamp.responseHandlers.FailureResponseHandler;
import com.employee_api.springboot_bootcamp.responseHandlers.SuccessResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @GetMapping
    public ResponseEntity<List<ProjectsDTO>> getAllSkills() {
        List<ProjectsDTO> projects = projectsService.getAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
