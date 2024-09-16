package com.example.trial_task.dto;

import com.example.trial_task.model.ContactType;

public record ContactDTO(
        Integer id,
        ContactType type,
        String value,
        Integer clientId
) {
}