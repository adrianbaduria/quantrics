package com.quantrics.codingexamadrianbaduria.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.quantrics.codingexamadrianbaduria.dto.Place;
import com.quantrics.codingexamadrianbaduria.external.ISSApi;
import com.quantrics.codingexamadrianbaduria.external.WikipediaApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@SpringBootTest
class ISSLocationServiceTest {

    @Autowired
    private ISSLocationService issLocationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @MockBean
    private ISSApi issApi;

    @MockBean
    private WikipediaApi wikipediaApi;

    @Test
    void testGetISSLocation() throws Exception {
        // Mock ISSApi response
        ObjectNode issPosition = objectMapper.createObjectNode()
                .put("latitude", 12.34)
                .put("longitude", 56.78);

        ObjectNode issLocationResponse = objectMapper.createObjectNode()
                .set("iss_position", issPosition);

        when(issApi.getISSLocation()).thenReturn(issLocationResponse);

        String jsonFilePath = "src/main/resources/place.json";
        List<Place> placeList = readJsonFile(jsonFilePath);
        when(wikipediaApi.getLocationDetails(anyDouble(), anyDouble())).thenReturn(placeList);

        // Call the service method
        List<Place> result = issLocationService.getISSLocation();

        // Verify the result
        assertEquals(placeList, result);
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
