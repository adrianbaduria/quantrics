package com.quantrics.codingexamadrianbaduria.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantrics.codingexamadrianbaduria.dto.Place;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WikipediaApi {

    private static final Logger logger = LoggerFactory.getLogger(WikipediaApi.class);

    @Value("${api.wikipedia.url}")
    private String wikipediaUrl;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Place> getLocationDetails(double latitude, double longitude) {
        String wikipediaUrlString = wikipediaUrl +
                latitude + "|" + longitude + "&format=json";
        logger.info("wikipediaUrlString: {}", wikipediaUrlString);

        String wikipediaResponse = restTemplate.getForObject(wikipediaUrlString, String.class);

        logger.info("wikipediaResponse: {}", wikipediaResponse);

        // Extract relevant information from Wikipedia response
        JsonNode placesJson = parseJson(wikipediaResponse).path("query").path("geosearch");
        return extractPlaces(placesJson);
    }

    public JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    private List<Place> extractPlaces(JsonNode placesJson) {
        List<Place> places = new ArrayList<>();
        for (JsonNode placeJson : placesJson) {
            Place place = new Place(
                    placeJson.get("title").asText(),
                    placeJson.get("lat").asDouble(),
                    placeJson.get("lon").asDouble(),
                    placeJson.path("country").asText()
            );
            places.add(place);
        }
        return places;
    }


}
