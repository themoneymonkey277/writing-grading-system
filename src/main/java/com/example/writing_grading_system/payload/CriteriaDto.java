package com.example.writing_grading_system.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CriteriaDto Model Information")
public class CriteriaDto {
    @Schema(description = "Id of Criteria")
    private Long id;

    @Schema(description = "Name of Criteria")
    private String name;

    @Schema(description = "Description of Criteria")
    private String description;

    @Schema(description = "Max point of Criteria")
    private Float maxPoint;
}
