package com.example.writing_grading_system.entity;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WritingTask> writingTasks;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmissionTask> submissionsTasks;
}
