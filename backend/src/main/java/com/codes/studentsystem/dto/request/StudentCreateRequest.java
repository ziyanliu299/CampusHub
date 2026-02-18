package com.codes.studentsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentCreateRequest {

    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name must be <= 100 characters")
    private String name;

    @NotBlank(message = "address is required")
    @Size(max = 200, message = "address must be <= 200 characters")
    private String address;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
