package com.employee_api.springboot_bootcamp.skills;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillsService {

    private final SkillsRepository skillsRepository;
    private final SkillsDTOMapper skillsDTOMapper;

    public List<SkillsDTO> getAll() {
        return skillsDTOMapper.skillsToDtoSkills(skillsRepository.findAll());
    }
}
