package com.quantrics.codingexamadrianbaduria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    private String title;
    private double latitude;
    private double longitude;
    private String country;

}
