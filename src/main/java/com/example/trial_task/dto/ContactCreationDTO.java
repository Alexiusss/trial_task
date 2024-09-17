package com.example.trial_task.dto;

import com.example.trial_task.model.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactCreationDTO(
        @NotNull
        ContactType type,
        @NotBlank
        @Size(min = 2, max = 128)
        String value,
        @NotNull
        Integer clientId
) {
}