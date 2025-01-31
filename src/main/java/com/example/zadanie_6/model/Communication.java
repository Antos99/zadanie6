package com.example.zadanie_6.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Communication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(columnDefinition = "BYTEA")
    @Basic(fetch = FetchType.LAZY)
    private byte[] body;
    @Column(columnDefinition = "BYTEA")
    @Basic(fetch = FetchType.LAZY)
    private byte[] deliverySettings;
    private long size;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private boolean isProcessed;
    private boolean isSent;
}
