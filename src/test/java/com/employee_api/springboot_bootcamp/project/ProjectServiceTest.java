package com.employee_api.springboot_bootcamp.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectMapper projectMapper;

    @InjectMocks
    ProjectService underTest;

    private Project project;
    private Project project2;
    private ProjectDTO projectDTO;
    private ProjectDTO projectDTO2;
    private ProjectAllDTO projectAllDTO;
    private List<Project> projects;
    private List<ProjectDTO> projectsDTO;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String name = "PZU";
        String description = "PZU project description.";
        project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);

        projectDTO = new ProjectDTO(id, name);
        projectAllDTO = new ProjectAllDTO(id, name, description);

        Long id2 = 2L;
        String name2 = "Orlen";
        String description2 = "Orlen project description.";
        project2 = new Project();
        project2.setId(id2);
        project2.setName(name2);
        project2.setDescription(description2);

        projectDTO2 = new ProjectDTO(id2, name2);

        projects = new ArrayList<>();
        projects.add(project);
        projects.add(project2);

        projectsDTO = new ArrayList<>();
        projectsDTO.add(projectDTO);
        projectsDTO.add(projectDTO2);
    }

    @Test
    void projectServiceIsCreated() {
        assertThat(underTest).isNotNull();
    }

    @Test
    void givenNoProjectsShouldReturnEmptyProjectList() {
        List<Project> emptyList = new ArrayList<>();
        when(projectRepository.findAll()).thenReturn(emptyList);
        List<ProjectDTO> result = underTest.getAll();
        assertThat(result).isEqualTo(emptyList);
    }

    @Test
    void givenProjectsShouldReturnProjectList() {
        when(projectRepository.findAll()).thenReturn(projects);
        when(projectMapper.mapToDto(project)).thenReturn(projectDTO);
        when(projectMapper.mapToDto(project2)).thenReturn(projectDTO2);
        List<ProjectDTO> result = underTest.getAll();
        assertThat(result).isEqualTo(projectsDTO);
    }

    @Test
    void givenIdThrowNoSuchElementExceptionOnGet() {
        Long nonExistingId = 3L;
        when(projectRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> underTest.getById(nonExistingId));
    }

    @Test
    void givenIdReturnProject() throws NoSuchElementException {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectMapper.mapToAllDto(project)).thenReturn(projectAllDTO);
        ProjectAllDTO result = underTest.getById(project.getId());
        assertThat(result).isEqualTo(projectAllDTO);
    }
}