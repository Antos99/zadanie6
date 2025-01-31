package com.example.zadanie_6.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogMonitorService {

    @Value("${catalog}")
    private String catalog;
    private final FileSaverService fileSaverService;

    @Scheduled(initialDelay = 1)
    public void monitorCatalog() throws IOException, InterruptedException {
        Set<Path> alreadyPresentFiles = getAlreadyPresentFiles();
        alreadyPresentFiles.forEach(fileSaverService::saveLocalFile);
        doMonitoring();
    }

    private Set<Path> getAlreadyPresentFiles() throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(catalog))) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .collect(Collectors.toSet());
        }
    }

    private void doMonitoring() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path catalogPath = Paths.get(catalog);
        catalogPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String filename = event.context().toString();
                Path filePath = Path.of(catalog + "/" + filename);
                fileSaverService.saveLocalFile(filePath);
            }
            key.reset();
        }
    }

}
