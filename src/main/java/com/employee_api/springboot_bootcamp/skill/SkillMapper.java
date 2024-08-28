package com.employee_api.springboot_bootcamp.skill;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillDTO mapToDto(Skill skills);
}
