package com.employee_api.springboot_bootcamp.skills;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillsDTOMapper {
    List<SkillsDTO> skillsToDtoSkills(List<Skills> skills);
}
