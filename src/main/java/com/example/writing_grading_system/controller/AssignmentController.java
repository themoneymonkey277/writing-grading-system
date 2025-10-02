package com.example.writing_grading_system.controller;

import com.example.writing_grading_system.payload.AssignmentDto;
import com.example.writing_grading_system.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/assignments")
@Tag(
        name = "Assignments API"
)
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AssignmentDto>> getAllAssignment(){
        return ResponseEntity.ok(assignmentService.getAllAssignment());
    }

    @Operation(
            summary = "Assignment REST API",
            description = "Get an assignment REST API"
    )

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable("id") long assignmentId){
        return new ResponseEntity<>(assignmentService.getAssignmentById(assignmentId), HttpStatus.OK);
    }


}
