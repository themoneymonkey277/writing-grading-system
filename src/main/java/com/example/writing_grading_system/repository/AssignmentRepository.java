package com.example.writing_grading_system.repository;

import com.example.writing_grading_system.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
