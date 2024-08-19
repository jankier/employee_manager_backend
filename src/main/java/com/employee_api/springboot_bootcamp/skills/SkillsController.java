package com.employee_api.springboot_bootcamp.skills;

import com.employee_api.springboot_bootcamp.responseHandlers.FailureResponseHandler;
import com.employee_api.springboot_bootcamp.responseHandlers.SuccessResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/skills")
@RequiredArgsConstructor
public class SkillsController {

    private final SkillsService skillsService;

    @GetMapping
    public ResponseEntity<List<SkillsDTO>> getAllProjects() {
        List<SkillsDTO> skills = skillsService.getAll();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
}
