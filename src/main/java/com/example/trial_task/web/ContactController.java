package com.example.trial_task.web;

import com.example.trial_task.dto.ContactCreationDTO;
import com.example.trial_task.dto.ContactDTO;
import com.example.trial_task.model.ContactType;
import com.example.trial_task.service.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Contact API", description = "Operations related to managing contacts")
public class ContactController {
    public static final String REST_URL = "/api/v1/clients/{id}/contacts";

    private final IContactService contactService;

    @Operation(summary = "Create a new contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PostMapping
    public ResponseEntity<ContactDTO> add(
            @RequestBody @Valid ContactCreationDTO contact,
            @Parameter(description = "ID of the client", required = true) @PathVariable Integer id
    ) {
        ContactDTO created = contactService.create(contact, id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Get all contacts by client ID", description = "Retrieve all contacts grouped by type for a given client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping
    public ResponseEntity<Map<ContactType, List<ContactDTO>>> getAllByClient(
            @Parameter(description = "ID of the client", required = true) @PathVariable Integer id) {
        Map<ContactType, List<ContactDTO>> contacts = contactService.getAllByClientId(id);
        return ResponseEntity.ok(contacts);
    }

    @Operation(summary = "Get contacts by client ID and contact type", description = "Retrieve all contacts of a specific type for a given client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client or contact type not found"),
    })
    @GetMapping("/{type}")
    public ResponseEntity<List<ContactDTO>> getAllByClientAndType(
            @Parameter(description = "Type of the contact", required = true) @PathVariable String type,
            @Parameter(description = "ID of the client", required = true) @PathVariable Integer id) {
        List<ContactDTO> contacts = contactService.getAllByTypeAndClientId(type, id);
        return ResponseEntity.ok(contacts);
    }
}