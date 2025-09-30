    package com.example.writing_grading_system.payload;

    import com.example.writing_grading_system.entity.ScoreDetail;
    import io.swagger.v3.oas.annotations.media.Schema;
    import jakarta.validation.constraints.NotEmpty;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.List;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Score Details Dto Model Information")
    public class ScoreDto {

        @Schema(description = "Id of Score Details Dto")
        private Long id;

        @Schema(description = "Points of Score Details Dto")
        private Float points;

        @Schema(description = "Criteria of Score Details Dto")
        private List<ScoreDetailDto> scoreDetails;

    }
