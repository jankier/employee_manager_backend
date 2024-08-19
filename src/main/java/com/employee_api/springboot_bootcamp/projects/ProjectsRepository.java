package com.employee_api.springboot_bootcamp.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Long> {
    Optional<Projects> findByName(String name);
}
