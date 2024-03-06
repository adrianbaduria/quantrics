package com.quantrics.codingexamadrianbaduria.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantrics.codingexamadrianbaduria.dto.Place;
import com.quantrics.codingexamadrianbaduria.exception.ApplicationException;
import com.quantrics.codingexamadrianbaduria.service.ISSLocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ISSLocationControllerTest {

    @Mock
    private ISSLocationService issLocationService;

    @InjectMocks
    private ISSLocationController issLocationController;

    @Test
    void getClosestPlaces_ReturnsPlacesList() throws IOException {
        // Arrange

        String jsonFilePath = "src/main/resources/place.json";
        List<Place> placeList = readJsonFile(jsonFilePath);
        when(issLocationService.getISSLocation()).thenReturn(placeList);

        // Act
        ResponseEntity<List<Place>> responseEntity = issLocationController.getClosestPlaces();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(placeList, responseEntity.getBody());
        verify(issLocationService, times(2)).getISSLocation();
    }

    @Test
    void getClosestPlaces_ThrowsExceptionWhenNoDataRetrieved() throws JsonProcessingException {
        // Arrange
        when(issLocationService.getISSLocation()).thenReturn(Collections.emptyList());

        // Act and Assert
        assertThrows(ApplicationException.class, () -> issLocationController.getClosestPlaces());
        verify(issLocationService, times(1)).getISSLocation();
    }

    private static List<Place> readJsonFile(String jsonFilePath) throws IOException {
        Path path = Paths.get(jsonFilePath);
        byte[] bytes = Files.readAllBytes(path);
        String JSON_DATA = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert JSON to List<Place>
        List<Place> places = Arrays.asList(objectMapper.readValue(JSON_DATA, Place[].class));

        return places;
    }
}
