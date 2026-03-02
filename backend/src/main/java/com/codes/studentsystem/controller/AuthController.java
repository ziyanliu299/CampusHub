package com.codes.studentsystem.controller;


import com.codes.studentsystem.dto.request.AuthLoginRequest;
import com.codes.studentsystem.dto.request.AuthRegisterRequest;
import com.codes.studentsystem.dto.response.AuthResponse;
import com.codes.studentsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody AuthRegisterRequest request) {
        return authService.register(request);
    }
   
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthLoginRequest request) {
        return authService.login(request);
    }
}
