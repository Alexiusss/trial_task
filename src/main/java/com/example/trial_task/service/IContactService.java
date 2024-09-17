package com.example.trial_task.service;

import com.example.trial_task.dto.ContactCreationDTO;
import com.example.trial_task.dto.ContactDTO;
import com.example.trial_task.model.ContactType;

import java.util.List;
import java.util.Map;

public interface IContactService {
    ContactDTO create(ContactCreationDTO client, Integer id);

    Map<ContactType, List<ContactDTO>> getAllByClientId(Integer clientId);

    List<ContactDTO> getAllByTypeAndClientId(String type, Integer clientId);
}