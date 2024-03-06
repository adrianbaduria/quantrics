package com.quantrics.codingexamadrianbaduria.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.quantrics.codingexamadrianbaduria.dto.Place;
import com.quantrics.codingexamadrianbaduria.external.ISSApi;
import com.quantrics.codingexamadrianbaduria.external.WikipediaApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ISSLocationService {

    private final ISSApi issApi;

    private final WikipediaApi wikipediaApi;

    public List<Place> getISSLocation() throws JsonProcessingException {
        JsonNode issLocation = issApi.getISSLocation();
        double latitude = issLocation.get("iss_position").get("latitude").asDouble();
        double longitude = issLocation.get("iss_position").get("latitude").asDouble();
        return wikipediaApi.getLocationDetails(latitude, longitude);
    }

}
