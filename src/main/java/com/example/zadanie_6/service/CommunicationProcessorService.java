package com.example.zadanie_6.service;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.NotificationType;
import com.example.zadanie_6.repository.CommunicationRepository;
import com.example.zadanie_6.util.CommunicationUtil;
import com.example.zadanie_6.util.FakeProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationProcessorService {

    private static final String TYPE_KEY = "type";
    private final CommunicationRepository communicationRepository;

    @Transactional
    @Scheduled(fixedDelayString = "${process.interval}", initialDelayString = "${process.initial-delay}")
    public void process() {
        log.info("Processing of communication started");
        List<Communication> unprocessedCommunications = communicationRepository.findByIsProcessed(false);
        unprocessedCommunications.forEach(this::doProcess);
        log.info("Processing of communication finished");
    }

    private void doProcess(Communication communication) {
        if (communication.getBody() == null || communication.getDeliverySettings() == null) {
            log.warn("Communication '{}' is not completed. Each communication should have body and delivery settings" +
                " files", communication.getName());
            return;
        }

        Map<String, String> deliverySettings = CommunicationUtil.getDeliverySettings(communication);
        NotificationType notificationType = NotificationType.valueOf(deliverySettings.get(TYPE_KEY));
        log.info("Start processing communication '{}' ...", communication.getName());
        FakeProcessor.process(1, 6);
        log.info("End processing communication '{}' ...", communication.getName());
        communication.setType(notificationType);
        communication.setProcessed(true);
    }
}
