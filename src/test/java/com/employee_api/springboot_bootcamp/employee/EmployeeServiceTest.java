package com.employee_api.springboot_bootcamp.employee;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService underTest;

    private Employee employee;
    private Employee employee2;
    private EmployeeDTO employeeDTO;
    private EmployeeDTO employeeDTO2;
    private List<Employee> employees;
    private List<EmployeeDTO> employeesDTO;


    @BeforeEach
    void setUp() {
        UUID id = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        Date employmentDate = new Date();

        employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setEmploymentDate(employmentDate.toString());

        employeeDTO = new EmployeeDTO(id, name, surname, employmentDate.toString(), null, null, null);

        ManagerDTO managerDTO = new ManagerDTO(id, name, surname);

        UUID id2 = UUID.randomUUID();
        String name2 = "Adam";
        String surname2 = "Sandler";
        Date employmentDate2 = new Date();

        employee2 = new Employee();
        employee2.setId(id2);
        employee2.setName(name2);
        employee2.setSurname(surname2);
        employee2.setEmploymentDate(employmentDate2.toString());
        employee2.setManager(employee);

        employeeDTO2 = new EmployeeDTO(id2, name2, surname2, employmentDate2.toString(), null, null, managerDTO);

        employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee2);

        employeesDTO = new ArrayList<>();
        employeesDTO.add(employeeDTO);
        employeesDTO.add(employeeDTO2);
    }

    @Test
    void employeeServiceShouldBeCreated() {
        assertThat(underTest).isNotNull();
    }

    @Test
    void givenNoEmployeesShouldReturnEmptyEmployeeList() {
        List<Employee> emptyList = new ArrayList<>();
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(emptyList);
        List<EmployeeDTO> result = underTest.getAll();
        assertThat(result).isEqualTo(emptyList);
    }

    @Test
    void givenEmployeesShouldReturnEmployeeList() {
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(employees);
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);
        when(employeeMapper.mapToDto(employee2)).thenReturn(employeeDTO2);
        List<EmployeeDTO> result = underTest.getAll();
        assertThat(result).isEqualTo(employeesDTO);
    }

    @Test
    void givenNonExistentIdShouldThrowNoSuchElementExceptionOnGetEmployee() {
        UUID nonExistingId = UUID.randomUUID();
        when(employeeRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> underTest.getById(nonExistingId));
    }

    @Test
    void givenIdShouldReturnEmployee() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);
        EmployeeDTO result = underTest.getById(employee.getId());
        assertThat(result).isEqualTo(employeeDTO);
    }

    @Test
    void givenIdShouldReturnEmptyManagersList() {
        List<EmployeeDTO> emptyManagerList = new ArrayList<>();
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(employees);
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);
        when(employeeMapper.mapToDto(employee2)).thenReturn(employeeDTO2);
        List<EmployeeDTO> result = underTest.getManagers(employee.getId());
        assertThat(result).isEqualTo(emptyManagerList);
    }

    @Test
    void givenIdShouldReturnManagersList() {
        List<EmployeeDTO> managerList = new ArrayList<>();
        managerList.add(employeeDTO);
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(employees);
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);
        when(employeeMapper.mapToDto(employee2)).thenReturn(employeeDTO2);
        List<EmployeeDTO> result = underTest.getManagers(employee2.getId());
        assertThat(result).isEqualTo(managerList);
    }

    @Test
    void givenValidRequestBodyShouldAddNewEmployee() {
        PostEmployeeDTO postEmployeeDTO = new PostEmployeeDTO(employeeDTO.id(), employeeDTO.name(), employeeDTO.surname(), employeeDTO.employmentDate(), null, null, null);
        when(employeeMapper.mapToEntity(postEmployeeDTO)).thenReturn(employee);
        underTest.create(postEmployeeDTO);
        verify(employeeRepository).save(employee);
    }

    @Test
    void givenInvalidRequestBodyShouldThrowConstraintViolationExceptionOnAddNewEmployee() {
        employee.setName("");
        PostEmployeeDTO postEmployeeDTO = new PostEmployeeDTO(employeeDTO.id(), "", employeeDTO.surname(), employeeDTO.employmentDate(), null, null, null);
        when(employeeMapper.mapToEntity(postEmployeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenThrow(ConstraintViolationException.class);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> underTest.create(postEmployeeDTO));
    }

    @Test
    void givenValidRequestBodyShouldModifyExistingEmployee() {
        EmployeeDTO updatedEmployee = new EmployeeDTO(
                employeeDTO.id(),
                employeeDTO2.name(),
                employeeDTO2.surname(),
                employeeDTO2.employmentDate(),
                employeeDTO2.skills(),
                employeeDTO2.projects(),
                employeeDTO2.manager()
        );
        when(employeeMapper.mapToEntity(updatedEmployee)).thenReturn(employee);
        underTest.update(employeeDTO, employeeDTO2);
        verify(employeeRepository).save(employee);
    }

    @Test
    void givenValidRequestBodyShouldDeleteExistingEmployee() {
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(employees);
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);
        when(employeeMapper.mapToDto(employee2)).thenReturn(employeeDTO2);
        when(employeeMapper.mapToEntity(employeeDTO2)).thenReturn(employee2);
        underTest.delete(employeeDTO2);
        verify(employeeRepository).delete(employee2);
    }

    @Test
    void givenEmployeeWhoIsManagerShouldThrowIllegalArgumentExceptionOnDeleteEmployee() {
        when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(employees);
        when(employeeMapper.mapToDto(employee)).thenReturn(employeeDTO);
        when(employeeMapper.mapToDto(employee2)).thenReturn(employeeDTO2);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> underTest.delete(employeeDTO));
    }
}