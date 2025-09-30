package com.example.writing_grading_system.repository;

import com.example.writing_grading_system.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
}
