package com.example.writing_grading_system.service.impl;

import com.example.writing_grading_system.entity.Score;
import com.example.writing_grading_system.entity.ScoreDetail;
import com.example.writing_grading_system.exception.ResourceNotFoundException;
import com.example.writing_grading_system.payload.ScoreDetailDto;
import com.example.writing_grading_system.repository.ScoreDetailRepository;
import com.example.writing_grading_system.repository.ScoreRepository;
import com.example.writing_grading_system.service.ScoreDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreDetailServiceImpl implements ScoreDetailService {

    ScoreRepository scoreRepository;
    ScoreDetailRepository scoreDetailRepository;
    ModelMapper mapper;

    public ScoreDetailServiceImpl(ScoreRepository scoreRepository, ScoreDetailRepository scoreDetailRepository){
        this.scoreDetailRepository = scoreDetailRepository;
        this.scoreRepository = scoreRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public ScoreDetailDto createScoreDetail(long scoreId, ScoreDetailDto scoreDetailDto) {

        ScoreDetail scoreDetail = mapToEntity(scoreDetailDto);

        Score score = scoreRepository.findById(scoreId).
                orElseThrow(() -> new ResourceNotFoundException("Score", "id", scoreId));

        scoreDetail.setScore(score);

        return mapToDto(scoreDetailRepository.save(scoreDetail));
    }

    @Override
    public List<ScoreDetailDto> scoreDetailDtos(long scoreId) {

        Score score = scoreRepository.findById(scoreId).
                orElseThrow(() -> new ResourceNotFoundException("Score", "id", scoreId));

        List<ScoreDetail> scoreDetails = scoreDetailRepository.findByScoreId(scoreId);

        return scoreDetails.stream().map(this::mapToDto).toList();
    }

    private ScoreDetailDto mapToDto(ScoreDetail scoreDetail){
        return mapper.map(scoreDetail,ScoreDetailDto.class);
    }

    private ScoreDetail mapToEntity(ScoreDetailDto scoreDetailDto){
        return mapper.map(scoreDetailDto,ScoreDetail.class);
    }
}
