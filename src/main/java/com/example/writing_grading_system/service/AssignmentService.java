package com.example.writing_grading_system.service;

import com.example.writing_grading_system.payload.AssignmentDto;

import java.util.List;

public interface AssignmentService {
    AssignmentDto CreateAssignmentFull(AssignmentDto assignmentDto, String imageUrl);
    List<AssignmentDto> getAllAssignment();
    AssignmentDto getAssignmentById(Long id);
}
