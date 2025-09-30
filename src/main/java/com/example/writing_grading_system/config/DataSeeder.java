package com.example.writing_grading_system.config;

import com.example.writing_grading_system.entity.Criteria;
import com.example.writing_grading_system.repository.CriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final CriteriaRepository criteriaRepository;
    @Override
    public void run(String... args){
        if(criteriaRepository.count() == 0){
            criteriaRepository.saveAll(List.of(
                    new Criteria(null,
                            "Task Achievement",
                            "Evaluates how well the candidate addresses the task, with relevant arguments and a clear position.",
                            9.0f),
                    new Criteria(null,
                            "Coherence and Cohesion",
                            "Assesses the logical flow of ideas and effective use of linking words and paragraphs.",
                            9.0f),
                    new Criteria(null,
                            "Lexical Resource",
                            "Evaluates vocabulary range, accuracy, and ability to paraphrase.",
                            9.0f),
                    new Criteria(null,
                            "Grammatical Range and Accuracy",
                            "Assesses grammar range, flexibility, and accuracy.",
                            9.0f)
            ));
        }
    }
}
