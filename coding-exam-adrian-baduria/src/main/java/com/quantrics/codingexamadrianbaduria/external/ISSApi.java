package com.quantrics.codingexamadrianbaduria.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantrics.codingexamadrianbaduria.config.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ISSApi {

    @Value("${api.open-notify.url}")
    private String issLocationUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    public JsonNode getISSLocation() throws JsonProcessingException {
        String issLocationResponse = restTemplate.getForObject(issLocationUrl, String.class);
        if(issLocationResponse != null) {
            // Use a JSON library to parse the response
            return objectMapper.readTree(issLocationResponse);
        } else {
            throw new RuntimeException("Api respond null value");
        }
    }

}
