package com.example.trial_task.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Contact extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ContactType type;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
