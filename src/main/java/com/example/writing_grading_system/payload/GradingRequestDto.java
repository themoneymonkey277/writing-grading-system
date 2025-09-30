package com.example.writing_grading_system.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradingRequestDto {

    private Long assignmentId;

    private List<WritingTaskDto> writingTasksV2;
    private List<SubmissionTaskDto> submissionTasksV2;
    private List<CriteriaDto> criteriaList;
}
