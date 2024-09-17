package com.example.trial_task.service;

import com.example.trial_task.dto.ClientCreationDTO;
import com.example.trial_task.dto.ClientDTO;

import java.util.List;

public interface IClientService {
    ClientDTO create(ClientCreationDTO client);

    List<ClientDTO> getAll();

    ClientDTO getById(Integer id);
}