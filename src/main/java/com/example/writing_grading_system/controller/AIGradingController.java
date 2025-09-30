package com.example.writing_grading_system.controller;

import com.example.writing_grading_system.payload.AiGeneratedTaskDto;
import com.example.writing_grading_system.payload.AssignmentDto;
import com.example.writing_grading_system.service.GeminiService;
import com.example.writing_grading_system.service.GradingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
@Tag(
        name = "AI Grading API"
)
public class AIGradingController {

    private final GeminiService geminiService;
    private final GradingService gradingService;

    public AIGradingController(GeminiService geminiService, GradingService gradingService){
        this.geminiService = geminiService;
        this.gradingService = gradingService;
    }

    @Operation(
            summary = "Grading REST API",
            description = "Grading a Submission REST API"
    )

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/generate-content")
    public ResponseEntity<String> generateContent(@RequestBody String prompt){
        String response = geminiService.generateContent(prompt);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/grading", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AssignmentDto> grading(@RequestPart("assignment") AssignmentDto assignmentRequest,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        AssignmentDto assignmentResponse = gradingService.gradeAssignment(assignmentRequest, file);
        return ResponseEntity.ok(assignmentResponse);
    }

    @GetMapping("/regrade/{id}")
    public ResponseEntity<AssignmentDto> regrade(@PathVariable("id") Long assignmentId) throws IOException {
        AssignmentDto assignmentResponse = gradingService.regradeAssignment(assignmentId);
        return ResponseEntity.ok(assignmentResponse);
    }

    @GetMapping("generate-task")
    public ResponseEntity<AiGeneratedTaskDto> generateTask() throws JsonProcessingException {
        AiGeneratedTaskDto response = geminiService.generateWritingTask();
        return ResponseEntity.ok(response);
    }

}
