package com.example.trial_task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientCreationDTO(
        @NotBlank
        @Size(min = 2, max = 128)
        String name
) {
}