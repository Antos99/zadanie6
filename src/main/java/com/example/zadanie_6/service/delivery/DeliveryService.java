package com.example.zadanie_6.service.delivery;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final List<DeliveryProcessor> deliveryProcessors;

    public void deliver(Communication communication) {
        if (communication.isSent()) {
            throw new RuntimeException(String.format("Communication '%s' has already been delivered",
                communication.getName()));
        }
        if (!communication.isProcessed()) {
            throw new RuntimeException(String.format("Communication '%s' has not been processed yet",
                communication.getName()));
        }

        switch(communication.getType()) {
            case EMAIL -> doDeliver(NotificationType.EMAIL, communication);
            case SMS -> doDeliver(NotificationType.SMS, communication);
            case PUSH -> doDeliver(NotificationType.PUSH, communication);
            default -> throw new RuntimeException(String.format("Communication type '%s' not supported",
                communication.getType()));
        }
    }

    @Async
    private void doDeliver(NotificationType notificationType, Communication communication) {
        DeliveryProcessor deliveryProcessor = getDeliveryProcessor(notificationType);
        deliveryProcessor.deliver(communication);
    }

    private DeliveryProcessor getDeliveryProcessor(NotificationType notificationType) {
        return deliveryProcessors.stream()
            .filter(p -> p.getNotificationType() == notificationType)
            .findFirst()
            .orElseThrow();
    }
}
