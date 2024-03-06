package com.quantrics.codingexamadrianbaduria.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ISSApiTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ISSApi issApi;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getISSLocation_Success() throws JsonProcessingException {
        // Arrange
        String mockResponse = "{\"key\":\"value\"}";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(mockResponse);

        // Act
        JsonNode result = issApi.getISSLocation();

        // Assert
        assertNotNull(result);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(String.class));
        //verify(objectMapper, times(1)).readTree(mockResponse);
    }

    @Test
    void getISSLocation_RestTemplateReturnsNull() throws JsonProcessingException {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> issApi.getISSLocation());
    }

}
