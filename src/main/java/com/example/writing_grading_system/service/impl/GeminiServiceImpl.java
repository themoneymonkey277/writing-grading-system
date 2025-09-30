package com.example.writing_grading_system.service.impl;

import com.example.writing_grading_system.payload.*;
import com.example.writing_grading_system.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Slf4j
@Service
public class GeminiServiceImpl implements GeminiService {

    private final ObjectMapper objectMapper;

    Dotenv dotenv = Dotenv.configure().load();
    private final String apiKey = dotenv.get("GEMINI_API_KEY");

    public GeminiServiceImpl(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateContent(String prompt) {
        try {
            Client client = Client.builder().apiKey(apiKey).build();
            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);
            return response.text();
        } catch (Exception e) {
            log.error("Error in generateContent", e);
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<SubmissionTaskDto> callGeminiWithPrompt(GradingRequestDto gradingRequestDto, byte[] imageData) {
        try {
            Client client = Client.builder().apiKey(apiKey).build();
            String gradingPrompt = buildGradingPrompt(gradingRequestDto);
            Content content;
            if (imageData != null && imageData.length > 0) {
                content = Content.fromParts(
                        Part.fromText(gradingPrompt),
                        Part.fromBytes(imageData, "image/png")
                );
            } else {
                content = Content.fromParts(
                        Part.fromText(gradingPrompt)
                );
            }
            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", content, null);
            return parseGradingResults(Objects.requireNonNull(response.text()));
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize grading request", e);
        }
    }

    @Override
    public AiGeneratedTaskDto generateWritingTask() throws JsonProcessingException {
        Client client = Client.builder().apiKey(apiKey).build();
        String prompt = aiGenerateTaskPrompt();
        Content content;
        content = Content.fromParts(
                Part.fromText(prompt)
        );
        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", content, null);
        return parseGeneratedTasks(Objects.requireNonNull(response.text()));
    }

    private String buildGradingPrompt(GradingRequestDto requestDtoV2) throws JsonProcessingException {
        String requestJson;
        String gradingPrompt = """
            You are an IELTS writing grader. You are given a GradingRequestDto including:
            - writingTasks: model answers for each task (i.e. Task 1 and Task 2 prompts).
            - submissionTasks: user's responses to those tasks.
            - criteriaList: evaluation criteria with maxPoint.
            
            Your task:
            1. For each submission task, compare its content with the corresponding writing task prompt.
                - If the submission content does NOT match the expected task (e.g., the user answered a Task 2 question in the Task 1 box), return a low score (e.g., max 4.0), and explain clearly in the feedback that the user submitted the wrong task.
            2. If the submission matches the task, grade it as a real IELTS examiner would:
                - Evaluate each task using the given criteria.
                - Calculate the average score across all criteria (round to nearest 0.5).
                - Provide feedback for each criterion.
            3. About task 1 image:
                - If task1Image is provided, strictly compare the submission content with the visual information in the image (charts, diagrams, tables, maps). Do not invent details not present in the image.
                - If task1Image is not provided, rely only on the text prompt for evaluation.
                - Penalize hallucinations: if the submission describes information that does not exist in the image, mention this clearly in the feedback and lower the score.
            4. About guardrails:
                - Do not assume or invent any data about the task image.
                - Only describe what is explicitly in the submission and image/prompt.
                - If unsure whether the submission matches the image, mark it as "mismatch" and assign a low score (<=4.0)
            5. For each task, return an object with the following structure:
            
            Return only a valid JSON array named `submissionTaskDtos`, where each element has this structure:
            {
              "id": long,
              "assignmentId": long,
              "taskNumber": int,
              "content": string,
              "score": {
                "points": float (round to .5 or .0),
                "scoreDetails": [
                  {
                    "feedback": string,
                    "points": float,
                    "criteria": {
                      "id": long,
                      "name": string,
                      "description": string,
                      "maxPoint": float
                    }
                  }
                ]
              }
            }
            
            Do not return any explanation, markdown, or extra fields outside the array.
            Return only valid JSON matching the structure above.
            """;


        requestJson = objectMapper.writeValueAsString(Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(
                                Map.of("text", gradingPrompt + objectMapper.writeValueAsString(requestDtoV2))
                        )
                ))
        ));
        return requestJson;
    }

    private String aiGenerateTaskPrompt(){
        return
            """
                You are an IELTS Task Generator.
                Generate an IELTS Writing Task 1 and Task 2.
                Return the result in JSON that matches the following DTO structure:
                {
                  "writingTaskDto": [
                    {
                      "taskNumber": 1,
                      "title": "Task 1",
                      "content": "Write a report describing the information shown in the chart.",
                      "imageUrl": null
                    },
                    {
                      "taskNumber": 2,
                      "title": "Task 2",
                      "content": "Some people think ... Discuss both views and give your opinion.",
                      "imageUrl": null
                    }
                  ],
                  "dataChartDto": {
                    "chartType": "bar",
                    "title": "Profit of companies...",
                    "labels": ["A", "B", "C"],
                    "values": [20, 40, 60],
                    "yAxisLabel": "Profit",
                    "xAxisLabel": "Company"
                  }
                }
                Notes:
                - Only return JSON, no extra explanations.
                - `imageUrl` is always null. The backend will generate the chart image from `dataChartDto` and upload to Cloudinary, then replace `imageUrl`.
                - `dataChartDto` should contain enough data to draw the chart (labels, values, and chartType).
            """;
    }

    private List<SubmissionTaskDto> parseGradingResults(String response) throws JsonProcessingException {
        String cleanedJson = response
                .replaceFirst("(?s)^```json\\s*", "")
                .replaceFirst("(?s)```\\s*$", "");
        return objectMapper.readValue(cleanedJson, new TypeReference<>() {});
    }

    private AiGeneratedTaskDto parseGeneratedTasks(String response) throws JsonProcessingException {
        String cleanedJson = response
                .replaceFirst("(?s)^```json\\s*", "")
                .replaceFirst("(?s)```\\s*$", "");
        return objectMapper.readValue(cleanedJson, new TypeReference<>() {});
    }
}
