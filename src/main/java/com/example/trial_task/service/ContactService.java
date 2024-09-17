package com.example.trial_task.service;

import com.example.trial_task.dto.ContactCreationDTO;
import com.example.trial_task.dto.ContactDTO;
import com.example.trial_task.model.Client;
import com.example.trial_task.model.Contact;
import com.example.trial_task.model.ContactType;
import com.example.trial_task.repository.ClientRepository;
import com.example.trial_task.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing contacts.
 * Implements {@link IContactService} interface.
 * Provides functionality to create and retrieve contacts associated with a client.
 */
@Service
@AllArgsConstructor
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;

    /**
     * Creates a new contact for a specified client.
     *
     * @param contact DTO containing the information needed to create a contact.
     * @return the created {@link ContactDTO}.
     */
    @Override
    public ContactDTO create(ContactCreationDTO contact) {
        Client client = clientRepository.getExisted(contact.clientId());
        Contact created = contactRepository.save(convertFromDTO(contact, client));
        return convertToDTO(created);
    }

    /**
     * Retrieves all contacts grouped by contact type for a specified client.
     *
     * @param clientId the ID of the client.
     * @return a map of {@link ContactType} to a list of {@link ContactDTO}.
     */
    @Override
    public Map<ContactType, List<ContactDTO>> getAllByClientId(Integer clientId) {
        List<Contact> contacts = contactRepository.getAllByClientId(clientId);
        return contacts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.groupingBy(ContactDTO::type, Collectors.toList()));
    }

    /**
     * Retrieves all contacts of a specified type for a specified client.
     *
     * @param type     the contact type as a string.
     * @param clientId the ID of the client.
     * @return a list of {@link ContactDTO} objects.
     */
    @Override
    public List<ContactDTO> getAllByTypeAndClientId(String type, Integer clientId) {
        List<Contact> contacts = contactRepository.getAllByTypeAndClientId(ContactType.valueOf(type), clientId);
        return contacts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a {@link Contact} entity to a {@link ContactDTO}.
     *
     * @param contact the contact entity.
     * @return the corresponding {@link ContactDTO}.
     */
    private ContactDTO convertToDTO(Contact contact) {
        return new ContactDTO(contact.id(), contact.getType(), contact.getValue(), contact.getClient().getId());
    }

    /**
     * Converts a {@link ContactCreationDTO} to a {@link Contact} entity.
     *
     * @param contact DTO with contact creation data.
     * @param client  the client entity associated with the contact.
     * @return the corresponding {@link Contact} entity.
     */
    private Contact convertFromDTO(ContactCreationDTO contact, Client client) {
        return new Contact(contact.type(), contact.value(), client);
    }
}