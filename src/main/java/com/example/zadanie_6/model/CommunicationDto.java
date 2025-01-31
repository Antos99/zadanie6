package com.example.zadanie_6.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunicationDto {
    private Long id;
    private String name;
    private Long size;
    private String body;
    private String deliverySettings;
    private NotificationType type;
    private Boolean isProcessed;
    private Boolean isSent;

    public static CommunicationDto fromEntity(Communication entity) {
        String body = entity.getBody() != null ? new String(entity.getBody()) : null;
        String deliverySettings = entity.getDeliverySettings() != null ? new String(entity.getDeliverySettings()) : null;
        return CommunicationDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .body(body)
            .deliverySettings(deliverySettings)
            .size(entity.getSize())
            .type(entity.getType())
            .isProcessed(entity.isProcessed())
            .isSent(entity.isSent())
            .build();
    }
}
