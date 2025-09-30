package com.example.writing_grading_system.service;


import com.example.writing_grading_system.payload.ScoreDto;

public interface ScoreService {
    ScoreDto createScoreFull(long submissionTaskId, ScoreDto scoreDto);
}
