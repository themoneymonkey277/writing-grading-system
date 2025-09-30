package com.example.writing_grading_system.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfig {
    Dotenv dotenv = Dotenv.configure().load();
    private final String cloud_name = dotenv.get("CLOUDINARY_NAME");
    private final String api_key = dotenv.get("CLOUDINARY_API_KEY");
    private final String secret_key = dotenv.get("CLOUDINARY_API_SECRET");
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloud_name,
                "api_key", api_key,
                "api_secret", secret_key,
                "secure", true));
    }
}
