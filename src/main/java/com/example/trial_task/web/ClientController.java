package com.example.trial_task.web;

import com.example.trial_task.dto.ClientCreationDTO;
import com.example.trial_task.dto.ClientDTO;
import com.example.trial_task.service.ClientService;
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
public class ClientController {
    public static final String REST_URL = "/api/v1/clients";
    
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> add(@RequestBody  @Valid ClientCreationDTO client) {
        ClientDTO created = clientService.create(client);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<ClientDTO> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Integer id) {
        ClientDTO client = clientService.getById(id);
        return ResponseEntity.ok(client);
    }
}