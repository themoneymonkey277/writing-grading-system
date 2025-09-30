package com.example.writing_grading_system.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "WritingTaskDto Model Information"
)
public class WritingTaskDto {
    @Schema(description = "Id of Writing Task")
    private Long id;

    @Schema(description = "Id of assignment")
    private Long assignmentId;

    @Schema(description = "Writing Task Content")
    @Size(min = 20, message = "Writing task content should have at least 2 characters")
    private String content;

    @Schema(description = "URL of image")
    private String imageUrl;

    @Schema(description = "Writing Task number (must be 1 or 2)")
    @Min(value = 1, message = "Task number must be 1 or 2")
    @Max(value = 2, message = "Task number must be 1 or 2")
    private Integer taskNumber;
}
