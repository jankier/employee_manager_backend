package com.employee_api.springboot_bootcamp.skill;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    SkillRepository skillRepository;

    @Mock
    SkillMapper skillMapper;

    @InjectMocks
    SkillService underTest;

    private Skill skill;
    private Skill skill2;
    private SkillDTO skillDTO;
    private SkillDTO skillDTO2;
    private List<Skill> skills;
    private List<SkillDTO> skillsDTO;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String name = "TypeScript";
        skill = new Skill();
        skill.setId(id);
        skill.setName(name);

        skillDTO = new SkillDTO(id, name);

        Long id2 = 2L;
        String name2 = "Java";
        skill2 = new Skill();
        skill2.setId(id2);
        skill2.setName(name2);

        skillDTO2 = new SkillDTO(id2, name2);

        skills = new ArrayList<>();
        skills.add(skill);
        skills.add(skill2);

        skillsDTO = new ArrayList<>();
        skillsDTO.add(skillDTO);
        skillsDTO.add(skillDTO2);

    }

    @Test
    void skillServiceShouldBeCreated() {
        assertThat(underTest).isNotNull();
    }

    @Test
    void givenNoSkillsShouldReturnEmptySkillList() {
        List<Skill> emptyList = new ArrayList<>();
        when(skillRepository.findAll()).thenReturn(emptyList);
        List<SkillDTO> result = underTest.getAll();
        assertThat(result).isEqualTo(emptyList);
    }

    @Test
    void givenSkillsShouldReturnSkillList() {
        when(skillRepository.findAll()).thenReturn(skills);
        when(skillMapper.mapToDto(skill)).thenReturn(skillDTO);
        when(skillMapper.mapToDto(skill2)).thenReturn(skillDTO2);
        List<SkillDTO> result = underTest.getAll();
        assertThat(result).isEqualTo(skillsDTO);
    }
}