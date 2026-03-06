package com.codes.studentsystem.service;
import com.codes.studentsystem.dto.request.AuthLoginRequest;
import com.codes.studentsystem.dto.request.AuthRegisterRequest;
import com.codes.studentsystem.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRegisterRequest request);
    AuthResponse login(AuthLoginRequest request);
}
