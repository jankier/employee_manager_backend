package com.employee_api.springboot_bootcamp.skill;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.employee_api.springboot_bootcamp.variables.Authority.ADMIN;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Transactional
    @PreAuthorize(ADMIN)
    public List<SkillDTO> getAll() {
        return skillRepository.findAll().stream().map(skillMapper::mapToDto).toList();
    }
}
