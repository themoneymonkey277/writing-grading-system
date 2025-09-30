package com.example.writing_grading_system.service;

import com.example.writing_grading_system.payload.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface GeminiService {
    String generateContent(String prompt);

    List<SubmissionTaskDto> callGeminiWithPrompt(GradingRequestDto gradingRequestDto, byte[] imageData);

    AiGeneratedTaskDto generateWritingTask() throws JsonProcessingException;
}
