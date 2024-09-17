package com.example.trial_task.repository;

import com.example.trial_task.error.NotFoundException;
import com.example.trial_task.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    default Client getExisted(Integer id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}