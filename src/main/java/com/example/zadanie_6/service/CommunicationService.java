package com.example.zadanie_6.service;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.CommunicationDto;
import com.example.zadanie_6.model.CommunicationUpdateRequest;
import com.example.zadanie_6.repository.CommunicationRepository;
import com.example.zadanie_6.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationService {

    private final CommunicationRepository communicationRepository;
    private final FileSaverService fileSaverService;
    private final DeliveryService deliveryService;

    public List<CommunicationDto> getAll() {
        return communicationRepository.findAll().stream()
            .map(CommunicationDto::fromEntity)
            .toList();
    }

    public CommunicationDto getById(long id) {
        Communication communication = communicationRepository.findById(id).orElseThrow();
        return CommunicationDto.fromEntity(communication);
    }

    public long create(MultipartFile body, MultipartFile deliverySettings) {
        try {
            fileSaverService.saveFile(body.getOriginalFilename(), body.getBytes());
            return fileSaverService.saveFile(deliverySettings.getOriginalFilename(), deliverySettings.getBytes());
        } catch (IOException e) {
            log.error("Error during saving files", e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public CommunicationDto update(CommunicationUpdateRequest updateRequest, MultipartFile body, MultipartFile deliverySettings) {
        Communication communication = communicationRepository.findById(updateRequest.getId()).orElseThrow();
        if (communication.isProcessed() && communication.isSent()) {
            throw new RuntimeException("Cannot update sent communication");
        }
        communication.setName(updateRequest.getName());
        communication.setProcessed(false);
        try {
            communication.setBody(body.getBytes());
            communication.setDeliverySettings(deliverySettings.getBytes());
        } catch (IOException e) {
            log.error("Error during updating files", e);
            throw new RuntimeException(e);
        }
        return CommunicationDto.fromEntity(communication);
    }


    public void delete(long id) {
        communicationRepository.deleteById(id);
    }

    @Transactional
    public void deliver(long id) {
        Communication communication = communicationRepository.findById(id).orElseThrow();
        deliveryService.deliver(communication);
        communication.setSent(true);
    }
}
