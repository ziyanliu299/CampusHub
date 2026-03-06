package com.codes.studentsystem.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRegisterRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private  String username;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    //"ADMIN" or "USER"
    @NotBlank
    private String role;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
