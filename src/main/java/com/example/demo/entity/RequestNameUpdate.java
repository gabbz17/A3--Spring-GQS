package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestNameUpdate {
    
    @NotBlank
    private String name;
}
