package com.example.trial_task.web;

import com.example.trial_task.dto.ClientCreationDTO;
import com.example.trial_task.dto.ClientDTO;
import com.example.trial_task.service.IClientService;
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

@RestController
@AllArgsConstructor
@RequestMapping(path = ClientController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Client API", description = "Operations related to managing clients")
public class ClientController {
    public static final String REST_URL = "/api/v1/clients";

    private final IClientService clientService;

    @PostMapping
    @Operation(summary = "Create a new client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientDTO> add(@RequestBody @Valid ClientCreationDTO client) {
        ClientDTO created = clientService.create(client);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    @Operation(summary = "Return a list of clients")
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<ClientDTO> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client data received successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientDTO> getById(
            @Parameter(description = "ID of the client", required = true) @PathVariable Integer id
    ) {
        ClientDTO client = clientService.getById(id);
        return ResponseEntity.ok(client);
    }
}