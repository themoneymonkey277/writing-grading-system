package com.example.writing_grading_system.service;

import com.example.writing_grading_system.payload.ScoreDetailDto;

import java.util.List;

public interface ScoreDetailService {

    ScoreDetailDto createScoreDetail(long scoreId, ScoreDetailDto scoreDetailDto);

    List<ScoreDetailDto> scoreDetailDtos(long scoreId);

}
