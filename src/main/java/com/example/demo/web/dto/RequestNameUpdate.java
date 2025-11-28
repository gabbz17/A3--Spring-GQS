package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestNameUpdate(
        @NotBlank
        String name
) {
}
