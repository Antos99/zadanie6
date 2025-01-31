package com.example.zadanie_6.util;

import com.example.zadanie_6.model.Communication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class CommunicationUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Map<String, String> getDeliverySettings(Communication communication) {
        try {
            return MAPPER.readValue(communication.getDeliverySettings(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private CommunicationUtil() {}
}
