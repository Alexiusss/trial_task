package com.example.trial_task.repository;

import com.example.trial_task.model.Contact;
import com.example.trial_task.model.ContactType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @EntityGraph(attributePaths = "client", type = EntityGraph.EntityGraphType.LOAD)
    List<Contact> getAllByClientId(Integer clientId);

    @EntityGraph(attributePaths = "client", type = EntityGraph.EntityGraphType.LOAD)
    List<Contact> getAllByTypeAndClientId(ContactType type, Integer clientId);
}