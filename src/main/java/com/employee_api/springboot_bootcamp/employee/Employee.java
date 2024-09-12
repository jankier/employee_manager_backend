package com.employee_api.springboot_bootcamp.employee;

import com.employee_api.springboot_bootcamp.enums.Role;
import com.employee_api.springboot_bootcamp.project.Project;
import com.employee_api.springboot_bootcamp.skill.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "employee")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 3, max = 25, message = "Invalid name length")
    private String name;

    @NotEmpty(message = "Surname is mandatory")
    @Size(min = 3, max = 25, message = "Invalid surname length")
    private String surname;

    @NotEmpty(message = "Employment date is mandatory")
    private String employmentDate;

    @ManyToMany
    private List<Skill> skills;

    @ManyToMany
    private List<Project> projects;

    @ManyToOne
    private Employee manager;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
