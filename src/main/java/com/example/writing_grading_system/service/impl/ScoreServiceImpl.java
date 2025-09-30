package com.example.writing_grading_system.service.impl;

import com.example.writing_grading_system.entity.*;
import com.example.writing_grading_system.exception.ResourceNotFoundException;
import com.example.writing_grading_system.payload.ScoreDto;
import com.example.writing_grading_system.repository.ScoreRepository;
import com.example.writing_grading_system.repository.SubmissionTaskRepository;
import com.example.writing_grading_system.service.ScoreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final SubmissionTaskRepository submissionTaskRepository;
    private final ScoreRepository scoreRepository;
    private final ModelMapper mapper;

    public ScoreServiceImpl(ScoreRepository scoreRepository, SubmissionTaskRepository submissionTaskRepository){
        this.scoreRepository = scoreRepository;
        this.submissionTaskRepository = submissionTaskRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public ScoreDto createScoreFull(long submissionTaskId, ScoreDto scoreDto) {
        Score score =  mapper.map(scoreDto,Score.class);

        SubmissionTask submissionTask = submissionTaskRepository.findById(submissionTaskId).
                orElseThrow(() -> new ResourceNotFoundException("SubmissionTask", "id", submissionTaskId));

        for (ScoreDetail scoreDetail : score.getScoreDetails()){
            scoreDetail.setScore(score);
        }
        Score savedScore = scoreRepository.save(score);
        submissionTask.setScore(savedScore);
        submissionTaskRepository.save(submissionTask);
        return mapper.map(score,ScoreDto.class);
    }


}
