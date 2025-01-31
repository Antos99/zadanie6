package com.example.zadanie_6.service;

import com.example.zadanie_6.model.Communication;
import com.example.zadanie_6.model.FileExtension;
import com.example.zadanie_6.repository.CommunicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileSaverService {

    private final CommunicationRepository communicationRepository;

    public long saveLocalFile(Path filePath) {
        log.info("Saving local file: {}", filePath.getFileName());
        String filename = filePath.getFileName().toString();
        byte[] fileData = readFile(filePath);
        return saveFile(filename, fileData);
    }

    @Transactional
    public long saveFile(String filename, byte[] fileData) {
        log.info("Saving file: {}", filename);

        String communicationName = filename.split("\\.")[0];
        String fileExtension = filename.split("\\.")[1];

        Communication communication = communicationRepository.findByName(communicationName);
        if (communication == null) {
            communication = Communication.builder().name(communicationName).build();
        }

        if (FileExtension.txt == FileExtension.valueOf(fileExtension)) {
            communication.setBody(fileData);
            communication.setSize(fileData.length);
        } else if (FileExtension.json == FileExtension.valueOf(fileExtension)) {
            communication.setDeliverySettings(fileData);
        } else {
            throw new RuntimeException("Invalid file extension");
        }

        return communicationRepository.save(communication).getId();
    }

    private byte[] readFile(Path filePath) {
        byte[] fileData;
        try {
            fileData = Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("Failed to read file", e);
            throw new RuntimeException(e);
        }
        return fileData;
    }
}
