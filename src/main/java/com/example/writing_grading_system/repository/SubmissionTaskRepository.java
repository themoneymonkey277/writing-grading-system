package com.example.writing_grading_system.repository;

import com.example.writing_grading_system.entity.SubmissionTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionTaskRepository extends JpaRepository<SubmissionTask, Long> {
}
