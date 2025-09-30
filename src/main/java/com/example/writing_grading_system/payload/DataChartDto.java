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
@Schema(description = "Chart Data DTO for AI Generated Task 1")
public class DataChartDto {
    private String title;
    private List<String> labels;
    private List<Integer> values;
    private String chartType;
    private String yAxisLabel;
    private String xAxisLabel;
}
