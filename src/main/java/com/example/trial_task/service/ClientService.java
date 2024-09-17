package com.example.trial_task.service;

import com.example.trial_task.dto.ClientCreationDTO;
import com.example.trial_task.dto.ClientDTO;
import com.example.trial_task.model.Client;
import com.example.trial_task.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing clients.
 * Implements {@link IClientService} interface.
 * Provides functionality to create and retrieve client data.
 */
@Service
@AllArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    /**
     * Creates a new client.
     *
     * @param client DTO containing the information needed to create a client.
     * @return the created {@link ClientDTO}.
     */
    @Override
    public ClientDTO create(ClientCreationDTO client) {
        Client created = clientRepository.save(convertFromDTO(client));
        return convertToDTO(created);
    }

    /**
     * Retrieves all clients.
     *
     * @return a list of {@link ClientDTO} objects representing all clients.
     */
    @Override
    public List<ClientDTO> getAll() {
        return clientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a client by its ID.
     *
     * @param id the ID of the client.
     * @return the {@link ClientDTO} representing the client.
     */
    @Override
    public ClientDTO getById(Integer id) {
        Client client = clientRepository.getExisted(id);
        return convertToDTO(client);
    }

    /**
     * Converts a {@link ClientCreationDTO} to a {@link Client} entity.
     *
     * @param clientCreationDTO the DTO containing client creation data.
     * @return the corresponding {@link Client} entity.
     */
    private Client convertFromDTO(ClientCreationDTO clientCreationDTO) {
        return new Client(clientCreationDTO.name());
    }

    /**
     * Converts a {@link Client} entity to a {@link ClientDTO}.
     *
     * @param client the client entity.
     * @return the corresponding {@link ClientDTO}.
     */
    private ClientDTO convertToDTO(Client client) {
        return new ClientDTO(client.id(), client.getName());
    }
}