package com.example.writing_grading_system.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AssignmentDto Model Information")
public class AssignmentDto {
    @Schema(description = "Id of Assignment")
    private Long id;

    @Schema(description = "Writing Tasks of Assignment")
    private List<WritingTaskDto> writingTasks;

    @Schema(description = "Submissions task of Assignment")
    private List<SubmissionTaskDto> submissionsTasks;
}
