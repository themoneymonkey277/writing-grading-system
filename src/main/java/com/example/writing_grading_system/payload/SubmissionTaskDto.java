package com.example.writing_grading_system.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "SubmissionTaskDto Model Information"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmissionTaskDto {

    @Schema(description = "Id of Submission Task")
    private Long id;

    @Schema(description = "Id of assignment")
    private Long assignmentId;

    @Schema(description = "Submission Task Content")
    private String content;

    @Schema(description = "Submission Task number (must be 1 or 2)")
    @Min(value = 1, message = "Task number must be 1 or 2")
    @Max(value = 2, message = "Task number must be 1 or 2")
    private Integer taskNumber;

    @Schema(description = "Score of SubmissionTask")
    private ScoreDto score;

}
