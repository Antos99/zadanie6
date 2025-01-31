package com.example.zadanie_6.service.delivery.processor;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.NotificationType;
import com.example.zadanie_6.model.email.Email;
import com.example.zadanie_6.model.email.EmailAddress;
import com.example.zadanie_6.service.delivery.DeliveryProcessor;
import com.example.zadanie_6.util.CommunicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
public class EmailDeliveryProcessor implements DeliveryProcessor {

    private static final String ADDRESS_KEY = "address";
    private static final String SUBJECT_KEY = "subject";
    private final RestClient restClient = RestClient.create();

    @Value("${email.host}")
    private String emailHost;
    @Value("${email.token}")
    private String emailToken;
    @Value("${email.from}")
    private String emailFrom;

    @Override
    public void deliver(Communication communication) {
        Map<String, String> deliverySettings = CommunicationUtil.getDeliverySettings(communication);
        String address = deliverySettings.get(ADDRESS_KEY);
        String subject = deliverySettings.get(SUBJECT_KEY);
        String text = new String(communication.getBody());

        Email email = Email.builder()
            .from(new EmailAddress(emailFrom))
            .to(Collections.singletonList(new EmailAddress(address)))
            .subject(subject)
            .text(text)
            .build();

        ResponseEntity<Void> mailApiResponse = restClient.post()
            .uri(emailHost)
            .header("Content-Type", "application/json")
            .header("X-Requested-With", "XMLHttpRequest")
            .header("Authorization", "Bearer " + emailToken)
            .body(email)
            .retrieve()
            .toBodilessEntity();
        log.info("Response: {}", mailApiResponse);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.EMAIL;
    }
}
