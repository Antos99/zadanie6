package com.example.zadanie_6.controller;

import com.example.zadanie_6.model.CommunicationDto;
import com.example.zadanie_6.model.CommunicationUpdateRequest;
import com.example.zadanie_6.service.CommunicationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/communications")
@RequiredArgsConstructor
public class CRUDRestController {

    private final CommunicationService communicationService;

    @GetMapping("/")
    public ResponseEntity<List<CommunicationDto>> getAll() {
        return ResponseEntity.ok(communicationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunicationDto> getById(@PathVariable long id) {
        return ResponseEntity.ok(communicationService.getById(id));
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@RequestPart("body") MultipartFile body,
                                    @RequestPart("deliverySettings") MultipartFile deliverySettings) {
        long id = communicationService.create(body, deliverySettings);
        return ResponseEntity.created(URI.create("/api/v1/communications/" + id)).build();
    }

    @PutMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<CommunicationDto> update(@RequestPart("bodyFile") MultipartFile body,
                                    @RequestPart("deliverySettingsFile") MultipartFile deliverySettings,
                                    @Parameter(
                                        description = "JSON request body",
                                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommunicationUpdateRequest.class))
                                    )
                                    @RequestPart("updateData") CommunicationUpdateRequest updateRequest) {
        CommunicationDto communication = communicationService.update(updateRequest, body, deliverySettings);
        return ResponseEntity.ok(communication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        communicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<Void> deliver(@PathVariable long id) {
        communicationService.deliver(id);
        return ResponseEntity.noContent().build();
    }
}
