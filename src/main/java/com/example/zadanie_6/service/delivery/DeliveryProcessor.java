package com.example.zadanie_6.service.delivery;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.NotificationType;
import org.springframework.scheduling.annotation.Async;

public interface DeliveryProcessor {

    @Async
    void deliver(Communication communication);
    NotificationType getNotificationType();
}
