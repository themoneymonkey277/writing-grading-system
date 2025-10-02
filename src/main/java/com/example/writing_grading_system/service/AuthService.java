package com.example.writing_grading_system.service;

import com.example.writing_grading_system.payload.LoginDto;
import com.example.writing_grading_system.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
