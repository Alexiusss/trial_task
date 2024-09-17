package com.example.trial_task.web;

import com.example.trial_task.dto.ContactCreationDTO;
import com.example.trial_task.dto.ContactDTO;
import com.example.trial_task.model.ContactType;
import com.example.trial_task.service.ContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path = ContactController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {
    public static final String REST_URL = "/api/v1/clients/{id}/contacts";

    private final IContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDTO> add(@RequestBody @Valid ContactCreationDTO contact) {
        ContactDTO created = contactService.create(contact);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public ResponseEntity<Map<ContactType, List<ContactDTO>>> getAllByClient(@RequestParam Integer clientId) {
        Map<ContactType, List<ContactDTO>> contacts = contactService.getAllByClientId(clientId);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<ContactDTO>> getAllByTypeAndClient(@PathVariable String type, @RequestParam Integer clientId) {
        List<ContactDTO> contacts = contactService.getAllByTypeAndClientId(type, clientId);
        return ResponseEntity.ok(contacts);
    }
}