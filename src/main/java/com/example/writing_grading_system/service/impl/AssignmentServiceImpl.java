package com.example.writing_grading_system.service.impl;

import com.example.writing_grading_system.entity.Assignment;
import com.example.writing_grading_system.entity.SubmissionTask;
import com.example.writing_grading_system.entity.WritingTask;
import com.example.writing_grading_system.exception.ResourceNotFoundException;
import com.example.writing_grading_system.payload.AssignmentDto;
import com.example.writing_grading_system.repository.AssignmentRepository;
import com.example.writing_grading_system.service.AssignmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    private final ModelMapper mapper;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, ModelMapper mapper){
        this.assignmentRepository = assignmentRepository;
        this.mapper = new ModelMapper();
    }


    @Override
    public AssignmentDto CreateAssignmentFull(AssignmentDto assignmentDto, String imageUrl) {
        Assignment assignment = mapper.map(assignmentDto, Assignment.class);
        for(WritingTask writingTask : assignment.getWritingTasks()){
            writingTask.setAssignment(assignment);
            if(writingTask.getTaskNumber() == 1){
                writingTask.setImageUrl(imageUrl);
            }
        }
        for(SubmissionTask submissionTask : assignment.getSubmissionsTasks()){
            submissionTask.setAssignment(assignment);
        }

        return mapper.map(assignmentRepository.save(assignment), AssignmentDto.class);
    }

    @Override
    public List<AssignmentDto> getAllAssignment() {
        List<Assignment> assignmentList= assignmentRepository.findAll();
        return assignmentList.stream()
                .map(assignment -> mapper.map(assignment, AssignmentDto.class))
                .toList();

    }

    @Override
    public AssignmentDto getAssignmentById(Long id){
        Assignment assignment = assignmentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", id));
        return mapper.map(assignment, AssignmentDto.class);
    }

}
