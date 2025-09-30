package com.example.writing_grading_system.service.impl;

import com.example.writing_grading_system.entity.Assignment;
import com.example.writing_grading_system.exception.ResourceNotFoundException;
import com.example.writing_grading_system.payload.*;
import com.example.writing_grading_system.repository.AssignmentRepository;
import com.example.writing_grading_system.repository.CriteriaRepository;
import com.example.writing_grading_system.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GradingServiceImpl implements GradingService {
    private final AssignmentService assignmentService;
    private final CriteriaRepository criteriaRepository;
    private final AssignmentRepository assignmentRepository;
    private final ScoreService scoreService;
    private final ObjectMapper objectMapper;
    private final FileService fileService;
    private final GeminiService geminiService;
    public GradingServiceImpl(
            AssignmentService assignmentService,
            CriteriaRepository criteriaRepository,
            ScoreService scoreService,
            ObjectMapper objectMapper,
            FileService fileService,
            GeminiService geminiService,
            AssignmentRepository assignmentRepository
    ) {
        this.assignmentService = assignmentService;
        this.criteriaRepository = criteriaRepository;
        this.scoreService = scoreService;
        this.objectMapper = objectMapper;
        this.fileService = fileService;
        this.geminiService = geminiService;
        this.assignmentRepository= assignmentRepository;
    }

    @Override
    public AssignmentDto gradeAssignment(AssignmentDto assignmentDto, MultipartFile file) throws IOException {
        String imgUrl = null;
        byte[] fileDownload = null;

        if (file != null && !file.isEmpty()) {
            imgUrl = fileService.uploadFile(file);
            fileDownload = fileService.downloadFileFromUrl(imgUrl);
        }
        AssignmentDto savedAssignment = assignmentService.CreateAssignmentFull(assignmentDto, imgUrl);
        GradingRequestDto gradingRequestDto = prepareGradingRequest(savedAssignment);

        List<SubmissionTaskDto> submissionTaskDtosV2 = geminiService.callGeminiWithPrompt(gradingRequestDto, fileDownload);
        Map<Long, ScoreDto> scoreMap = new HashMap<>();

        for (SubmissionTaskDto dto : submissionTaskDtosV2) {
            ScoreDto scoreDto = scoreService.createScoreFull(dto.getId(), dto.getScore());
            scoreMap.put(dto.getId(), scoreDto);
        }

        for (SubmissionTaskDto task : savedAssignment.getSubmissionsTasks()) {
            ScoreDto matchedScore = scoreMap.get(task.getId());
            if (matchedScore != null) {
                task.setScore(matchedScore);
            }
        }
        return savedAssignment;
    }


    @Override
    public AssignmentDto regradeAssignment(Long assignmentId) throws IOException {
        Assignment assignment = assignmentRepository.findById(assignmentId).
                orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
        AssignmentDto assignmentDto = objectMapper.convertValue(assignment, AssignmentDto.class);
        String imgUrl = null;
        byte[] fileDownload = null;
        List<WritingTaskDto> writingTaskDtoV2 = assignmentDto.getWritingTasks();
        for (WritingTaskDto task : writingTaskDtoV2){
            if(task.getTaskNumber() == 1 && task.getImageUrl() != null){
                imgUrl = task.getImageUrl();
                fileDownload = fileService.downloadFileFromUrl(imgUrl);
            }
        }

        GradingRequestDto gradingRequestDtoV2 = prepareGradingRequest(assignmentDto);
        List<SubmissionTaskDto> submissionTaskDtosV2 = geminiService.callGeminiWithPrompt(gradingRequestDtoV2, fileDownload);
        Map<Long, ScoreDto> scoreMap = new HashMap<>();

        for (SubmissionTaskDto dto : submissionTaskDtosV2) {
            ScoreDto scoreDto = scoreService.createScoreFull(dto.getId(), dto.getScore());
            scoreMap.put(dto.getId(), scoreDto);
        }

        for (SubmissionTaskDto task : assignmentDto.getSubmissionsTasks()) {
            ScoreDto matchedScore = scoreMap.get(task.getId());
            if (matchedScore != null) {
                task.setScore(matchedScore);
            }
        }
        return assignmentDto;
    }

    private GradingRequestDto prepareGradingRequest(AssignmentDto assignmentDtoV2) {
        for (SubmissionTaskDto task : assignmentDtoV2.getSubmissionsTasks()) {
            task.setScore(null);
        }

        return new GradingRequestDto(
                assignmentDtoV2.getId(),
                assignmentDtoV2.getWritingTasks(),
                assignmentDtoV2.getSubmissionsTasks(),
                criteriaRepository.findAll().stream()
                        .map(c -> objectMapper.convertValue(c, CriteriaDto.class))
                        .toList()
        );
    }
}
