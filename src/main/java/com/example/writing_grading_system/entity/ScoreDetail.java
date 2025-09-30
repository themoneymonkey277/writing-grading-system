package com.example.writing_grading_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "score_details",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"score_id", "criteria_id"})
        }
)
public class ScoreDetail {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(name = "point")
    private Float points;


    @Column(name = "feedback", columnDefinition = "LONGTEXT")
    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criteria_id")
    @JsonIgnore
    private Criteria criteria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    @JsonIgnore
    private Score score;
}
