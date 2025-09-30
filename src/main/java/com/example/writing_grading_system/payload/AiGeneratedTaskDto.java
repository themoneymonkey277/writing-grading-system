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
@Schema(
        description = "AI Task Generated Model Information"
)
public class AiGeneratedTaskDto {

    @Schema(description = "WritingTask of AI Gen Task")
    private List<WritingTaskDto> writingTaskDto;

    @Schema(description = "Data of Chart")
    private DataChartDto dataChartDto;

}
