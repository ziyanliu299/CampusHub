package com.codes.studentsystem.service;
import com.codes.studentsystem.dto.request.AuthLoginRequest;
import com.codes.studentsystem.dto.request.AuthRegisterRequest;
import com.codes.studentsystem.dto.response.AuthResponse;
import com.codes.studentsystem.exception.ConflictException;
import com.codes.studentsystem.exception.NotFoundException;
import com.codes.studentsystem.model.AppUser;
import com.codes.studentsystem.model.Role;
import com.codes.studentsystem.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AppUserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(AuthRegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("username already exists");
        }
        AppUser u = new AppUser();
        u.setUsername(request.getUsername());
        u.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        u.setRole(Role.valueOf(request.getRole().toUpperCase()));

        userRepository.save(u);
        String token = jwtService.generateToken(u.getUsername(), u.getRole().name());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        AppUser u = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("user not found"));

        if (!passwordEncoder.matches(request.getPassword(), u.getPasswordHash())) {
            throw new NotFoundException("invalid credentials");
        }

        String token = jwtService.generateToken(u.getUsername(), u.getRole().name());
        return new AuthResponse(token);
    }

}
