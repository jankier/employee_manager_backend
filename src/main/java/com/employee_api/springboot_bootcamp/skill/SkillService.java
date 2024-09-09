package com.employee_api.springboot_bootcamp.skill;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SkillDTO> getAll() {
        return skillRepository.findAll().stream().map(skillMapper::mapToDto).collect(Collectors.toList());
    }
}
