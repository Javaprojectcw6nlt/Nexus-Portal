package com.example.employeeportal.repository;
import com.example.employeeportal.model.Project; import org.springframework.data.jpa.repository.JpaRepository;
 public interface ProjectRepository extends JpaRepository<Project,Long> {}
