package com.example.writing_grading_system.repository;

import com.example.writing_grading_system.entity.ScoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreDetailRepository extends JpaRepository<ScoreDetail, Long> {

    List<ScoreDetail> findByScoreId(@Param("score_id") long score_id);

}
