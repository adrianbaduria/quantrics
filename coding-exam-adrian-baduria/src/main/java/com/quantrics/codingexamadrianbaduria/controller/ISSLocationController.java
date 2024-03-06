package com.quantrics.codingexamadrianbaduria.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantrics.codingexamadrianbaduria.config.RestTemplateConfig;
import com.quantrics.codingexamadrianbaduria.dto.Place;
import com.quantrics.codingexamadrianbaduria.exception.ApplicationException;
import com.quantrics.codingexamadrianbaduria.service.ISSLocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
class ISSLocationController {

    private static final Logger logger = LoggerFactory.getLogger(ISSLocationController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplateConfig restTemplate;

    private final ISSLocationService issLocationService;

    @GetMapping("/get_closest_places")
    public ResponseEntity<List<Place>> getClosestPlaces() throws JsonProcessingException {
        List<Place> issLocation = issLocationService.getISSLocation();
        if(!issLocation.isEmpty()) {
            return new ResponseEntity<>(issLocationService.getISSLocation(), HttpStatus.OK);
        } else {
            throw new ApplicationException("No data retrieved");
        }
    }

}