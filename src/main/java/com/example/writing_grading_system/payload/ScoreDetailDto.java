package com.example.writing_grading_system.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ScoreDetailDto Model Information")
public class ScoreDetailDto {

    @Schema(description = "Id of ScoreDto")
    private Long id;

    @Schema(description = "Id of ScoreDto")
    @NotEmpty(message = "Id of ScoreDto should not be empty or null")
    private Long scoreId;

    @Schema(description = "Feedback of Score Detail Dto")
    @NotEmpty(message = "Feedback of Score Detail Dto should not be empty or null")
    private String feedback;

    @Schema(description = "Points of Score Detail Dto")
    @NotEmpty(message = "Points of ScoreDto should not be empty or null")
    private Float points;

    @Schema(description = "Criteria of Sore Detail Dto")
    @NotEmpty(message = "Criteria of Sore Detail Dto should not be empty or null")
    private CriteriaDto criteria;
}
