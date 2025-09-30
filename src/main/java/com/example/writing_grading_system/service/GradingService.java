package com.example.writing_grading_system.service;

import com.example.writing_grading_system.payload.AssignmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GradingService {
    AssignmentDto gradeAssignment(AssignmentDto assignmentDtoV2, MultipartFile file) throws IOException;

    AssignmentDto regradeAssignment(Long assignmentId) throws IOException;
}
