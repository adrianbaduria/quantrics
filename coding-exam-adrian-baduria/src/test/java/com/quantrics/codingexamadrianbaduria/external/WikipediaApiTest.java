package com.quantrics.codingexamadrianbaduria.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantrics.codingexamadrianbaduria.dto.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class WikipediaApiTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WikipediaApi wikipediaApi;

    @Value("${api.wikipedia.url}")
    private String wikipediaUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLocationDetails() {
        // Mocking restTemplate's behavior
        String mockResponse = "{\"query\":{\"geosearch\":[{\"title\":\"Place1\",\"lat\":1.0,\"lon\":2.0,\"country\":\"Country1\"}]}}";
        Mockito.when(restTemplate.getForObject(any(String.class), eq(String.class))).thenReturn(mockResponse);

        // Mocking the Wikipedia URL
        //wikipediaApi.setWikipediaUrl("https://mocked-wikipedia-url.com/api/query?list=geosearch&gscoord=");

        // Calling the service method
        List<Place> places = wikipediaApi.getLocationDetails(1.0, 2.0);

        // Asserting the result
        assertEquals(1, places.size());
        assertEquals("Place1", places.get(0).getTitle());
        assertEquals(1.0, places.get(0).getLatitude());
        assertEquals(2.0, places.get(0).getLongitude());
        assertEquals("Country1", places.get(0).getCountry());
    }
}
