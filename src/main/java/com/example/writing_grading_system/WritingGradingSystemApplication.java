package com.example.writing_grading_system;

import com.example.writing_grading_system.service.GeminiService;
import io.github.cdimascio.dotenv.Dotenv;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WritingGradingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WritingGradingSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

//	@Bean
//	CommandLineRunner run(GeminiService geminiService) {
//		return args -> {
//			String response = geminiService.generateContent("Explain how AI works");
//			System.out.println("Gemini response: " + response);
//		};
//	}

}
