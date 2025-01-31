package com.example.zadanie_6.service.delivery.processor;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.NotificationType;
import com.example.zadanie_6.service.delivery.DeliveryProcessor;
import com.example.zadanie_6.util.FakeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsDeliveryProcessor implements DeliveryProcessor {

    @Override
    public void deliver(Communication communication) {
        log.info("Start delivering push notification {}", communication.getName());
        FakeProcessor.process(5, 10);
        log.info("End delivering push notification {}", communication.getName());
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.SMS;
    }
}
